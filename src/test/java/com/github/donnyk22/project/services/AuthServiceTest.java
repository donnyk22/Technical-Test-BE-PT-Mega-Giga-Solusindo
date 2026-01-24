package com.github.donnyk22.project.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Date;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.github.donnyk22.project.exceptions.BadRequestException;
import com.github.donnyk22.project.exceptions.ResourceNotFoundException;
import com.github.donnyk22.project.models.dtos.UsersDto;
import com.github.donnyk22.project.models.entities.Users;
import com.github.donnyk22.project.models.forms.UserLoginForm;
import com.github.donnyk22.project.models.forms.UserRegisterForm;
import com.github.donnyk22.project.repositories.UsersRepository;
import com.github.donnyk22.project.services.auth.AuthServiceImpl;
import com.github.donnyk22.project.utils.AuthExtractUtil;
import com.github.donnyk22.project.utils.JwtUtil;
import com.github.donnyk22.project.utils.RedisTokenUtil;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.http.HttpServletRequest;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @InjectMocks
    private AuthServiceImpl authService;

    @Mock
    private UsersRepository usersRepository;

    @Mock
    private JwtUtil jwtUtil;

    @Mock
    private RedisTokenUtil redisTokenUtil;

    @Mock
    private AuthExtractUtil authExtractUtil;

    @Mock
    private HttpServletRequest httpServletRequest;

    // ================= REGISTER =================

    @Test
    void register_shouldThrowException_whenPasswordMismatch() {
        UserRegisterForm form = new UserRegisterForm()
            .setPassword("password")
            .setRePassword("wrong");

        BadRequestException ex = assertThrows(
            BadRequestException.class,
            () -> authService.register(form, httpServletRequest)
        );

        assertEquals("Retype password doesn't match. Please try again!", ex.getMessage());

        verify(usersRepository, never()).save(any());
    }

    @Test
    void register_shouldSaveUserAndReturnDto() {
        UserRegisterForm form = new UserRegisterForm()
            .setName("Donny")
            .setEmail("donny@test.com")
            .setPassword("password")
            .setRePassword("password");

        when(usersRepository.save(any(Users.class)))
            .thenAnswer(invocation -> invocation.getArgument(0));

        UsersDto result = authService.register(form, httpServletRequest);

        assertNotNull(result);
        assertEquals("Donny", result.getName());
        assertEquals("donny@test.com", result.getEmail());

        verify(usersRepository).save(any(Users.class));
    }

    // ================= LOGIN =================

    @Test
    void login_shouldThrowException_whenUserNotFound() {
        UserLoginForm form = new UserLoginForm()
            .setEmail("missing@test.com");

        when(usersRepository.findByEmail(form.getEmail()))
            .thenReturn(null);

        ResourceNotFoundException ex = assertThrows(
            ResourceNotFoundException.class,
            () -> authService.login(form, httpServletRequest)
        );

        assertEquals("User not found", ex.getMessage());
    }

    @Test
    void login_shouldThrowException_whenPasswordInvalid() {
        UserLoginForm form = new UserLoginForm()
            .setEmail("user@test.com")
            .setPassword("wrong");

        Users user = new Users()
            .setEmail(form.getEmail())
            .setPassword(new BCryptPasswordEncoder().encode("password"));

        when(usersRepository.findByEmail(form.getEmail()))
            .thenReturn(user);

        BadRequestException ex = assertThrows(
            BadRequestException.class,
            () -> authService.login(form, httpServletRequest)
        );

        assertEquals("Invalid email or password", ex.getMessage());

        verify(jwtUtil, never()).generateToken(any(), any(), any(), any());
        verify(redisTokenUtil, never()).storeToken(any(), any());
    }

    @Test
    void login_shouldReturnUserDto_whenCredentialsValid() {
        UserLoginForm form = new UserLoginForm()
            .setEmail("user@test.com")
            .setPassword("password");

        Users user = new Users()
            .setId(1)
            .setName("User")
            .setEmail(form.getEmail())
            .setRole("USER")
            .setPassword(new BCryptPasswordEncoder().encode("password"));

        String token = "jwt-token";

        Claims claims = Jwts.claims()
            .setIssuedAt(new Date())
            .setExpiration(new Date(System.currentTimeMillis() + 30_000));

        when(usersRepository.findByEmail(form.getEmail()))
            .thenReturn(user);
        when(jwtUtil.generateToken(any(), any(), any(), any()))
            .thenReturn(token);
        when(jwtUtil.extractClaims(token))
            .thenReturn(claims);
        when(redisTokenUtil.get(any(), any()))
            .thenReturn("0");

        UsersDto result = authService.login(form, httpServletRequest);

        assertEquals(token, result.getToken());
        assertEquals(claims.getIssuedAt().toInstant(), result.getIssuedAt());
        assertEquals(claims.getExpiration().toInstant(), result.getExpiresAt());

        verify(redisTokenUtil).deleteTokenByEmail(user.getEmail());
        verify(redisTokenUtil).storeToken(token, user.getEmail());
    }

    // ================= REFRESH =================

    @Test
    void refresh_shouldThrowException_whenUserNotFound() {
        String email = "missing@test.com";

        when(authExtractUtil.getUserEmail())
            .thenReturn(email);
        when(usersRepository.findByEmail(email))
            .thenReturn(null);

        ResourceNotFoundException ex = assertThrows(
            ResourceNotFoundException.class,
            () -> authService.refresh()
        );

        assertEquals("User not found", ex.getMessage());
    }

    @Test
    void refresh_shouldReturnNewToken_whenUserExists() {
        String email = "user@test.com";

        Users user = new Users()
            .setId(1)
            .setName("User")
            .setEmail(email)
            .setRole("USER");

        String token = "new-jwt-token";

        Claims claims = Jwts.claims()
            .setIssuedAt(new Date())
            .setExpiration(new Date(System.currentTimeMillis() + 60_000));

        when(authExtractUtil.getUserEmail())
            .thenReturn(email);
        when(usersRepository.findByEmail(email))
            .thenReturn(user);
        when(jwtUtil.generateToken(any(), any(), any(), any()))
            .thenReturn(token);
        when(jwtUtil.extractClaims(token))
            .thenReturn(claims);

        UsersDto result = authService.refresh();

        assertEquals(token, result.getToken());
        assertEquals(claims.getExpiration().toInstant(), result.getExpiresAt());

        verify(redisTokenUtil).deleteTokenByEmail(email);
        verify(redisTokenUtil).storeToken(token, email);
    }

    // ================= LOGOUT =================

    @Test
    void logout_shouldReturnTrue_whenNoAuthorizationHeader() {
        HttpServletRequest request = mock(HttpServletRequest.class);

        when(request.getHeader("Authorization"))
            .thenReturn(null);

        Boolean result = authService.logout(request);

        assertTrue(result);

        verify(redisTokenUtil, never()).deleteToken(any(), any());
    }

    @Test
    void logout_shouldDeleteToken_whenTokenIsValid() {
        HttpServletRequest request = mock(HttpServletRequest.class);

        when(request.getHeader("Authorization"))
            .thenReturn("Bearer valid-token");

        when(redisTokenUtil.isTokenValid("valid-token"))
            .thenReturn(true);

        when(authExtractUtil.getUserEmail())
            .thenReturn("user@test.com");

        Boolean result = authService.logout(request);

        assertTrue(result);

        verify(redisTokenUtil).deleteToken("valid-token", "user@test.com");
    }
    
}