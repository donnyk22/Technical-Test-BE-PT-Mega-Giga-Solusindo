package com.github.donnyk22.project.services.auth;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
import com.github.donnyk22.project.utils.AuthExtractUtil;
import com.github.donnyk22.project.utils.JwtUtil;
import com.github.donnyk22.project.utils.RedisTokenUtil;

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

    // ================= REGISTER =================

    @Test
    void register_shouldThrowException_whenPasswordMismatch() {
        UserRegisterForm form = new UserRegisterForm()
            .setPassword("password")
            .setRePassword("wrong");

        BadRequestException ex = assertThrows(
            BadRequestException.class,
            () -> authService.register(form)
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

        UsersDto result = authService.register(form);

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
            () -> authService.login(form)
        );

        assertEquals("User not found", ex.getMessage());
    }

    @Test
    void login_shouldThrowException_whenPasswordInvalid() {
        UserLoginForm form = new UserLoginForm()
            .setEmail("user@test.com")
            .setPassword("wrong");

        Users user = new Users()
            .setEmail("user@test.com")
            .setPassword(new BCryptPasswordEncoder().encode("password"));

        when(usersRepository.findByEmail(form.getEmail()))
            .thenReturn(user);

        BadRequestException ex = assertThrows(
            BadRequestException.class,
            () -> authService.login(form)
        );

        assertEquals("Invalid email or password", ex.getMessage());
    }

    @Test
    void login_shouldReuseExistingToken_whenTokenStillValid() {
        UserLoginForm form = new UserLoginForm()
            .setEmail("user@test.com")
            .setPassword("password");

        Users user = new Users()
            .setEmail("user@test.com")
            .setPassword(new BCryptPasswordEncoder().encode("password"));

        when(usersRepository.findByEmail(form.getEmail()))
            .thenReturn(user);

        when(redisTokenUtil.getTokenByEmail(user.getEmail()))
            .thenReturn("existing-token");

        when(redisTokenUtil.isTokenValid("existing-token"))
            .thenReturn(true);

        UsersDto result = authService.login(form);

        assertEquals("existing-token", result.getToken());

        verify(redisTokenUtil).refreshTokenTTL("existing-token", user.getEmail());
        verify(jwtUtil, never()).generateToken(any(), any(), any(), any());
    }

    @Test
    void login_shouldGenerateNewToken_whenNoExistingToken() {
        UserLoginForm form = new UserLoginForm()
            .setEmail("user@test.com")
            .setPassword("password");

        Users user = new Users()
            .setId(1)
            .setName("User")
            .setEmail("user@test.com")
            .setPassword(new BCryptPasswordEncoder().encode("password"))
            .setRole("USER");

        when(usersRepository.findByEmail(form.getEmail()))
            .thenReturn(user);

        when(redisTokenUtil.getTokenByEmail(user.getEmail()))
            .thenReturn(null);

        when(jwtUtil.generateToken(any(), any(), any(), any()))
            .thenReturn("jwt-token");

        UsersDto result = authService.login(form);

        assertNotNull(result);
        assertEquals("jwt-token", result.getToken());

        verify(redisTokenUtil).storeToken("jwt-token", user.getEmail());
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