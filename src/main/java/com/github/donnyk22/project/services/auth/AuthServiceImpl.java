package com.github.donnyk22.project.services.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.donnyk22.project.exceptions.BadRequestException;
import com.github.donnyk22.project.exceptions.ResourceNotFoundException;
import com.github.donnyk22.project.models.dtos.UsersDto;
import com.github.donnyk22.project.models.entities.Users;
import com.github.donnyk22.project.models.forms.UserLoginForm;
import com.github.donnyk22.project.models.forms.UserRegisterForm;
import com.github.donnyk22.project.models.mappers.UsersMapper;
import com.github.donnyk22.project.repositories.UsersRepository;
import com.github.donnyk22.project.utils.AuthExtractUtil;
import com.github.donnyk22.project.utils.JwtUtil;
import com.github.donnyk22.project.utils.RedisTokenUtil;

import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;

@Service
@Transactional
public class AuthServiceImpl implements AuthService{

    @Autowired UsersRepository usersRepository;
    @Autowired JwtUtil jwtUtil;
    @Autowired RedisTokenUtil redisTokenUtil;
    @Autowired AuthExtractUtil authExtractUtil;

    @Override
    public UsersDto register(UserRegisterForm form) {
        if(!form.getPassword().equals(form.getRePassword())){
            throw new BadRequestException("Retype password doesn't match. Please try again!");
        }
        Users user = UsersMapper.toEntity(form, new BCryptPasswordEncoder().encode(form.getPassword()));
        if(user == null){
            throw new BadRequestException("Failed to register a new user. Please try again");
        }
        usersRepository.save(user);
        return UsersMapper.toBaseDto(user);
    }

    @Override
    public UsersDto login(UserLoginForm form) {
        Users user = usersRepository.findByEmail(form.getEmail());
        if(user == null){
            throw new ResourceNotFoundException("User not found");
        }
        Boolean passwordMatch = new BCryptPasswordEncoder().matches(form.getPassword(), user.getPassword());
        if(!passwordMatch){
            throw new BadRequestException("Invalid email or password");
        }

        return refreshToken(user);
    }

    @Override
    public UsersDto refresh() {
        String email = authExtractUtil.getUserEmail();

        Users user = usersRepository.findByEmail(email);
        if(user == null){
            throw new ResourceNotFoundException("User not found");
        }

        return refreshToken(user);
    }

    @Override
    public Boolean logout(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        if(header != null) {
            String token;
            if(header.startsWith("Bearer ")){
                token = header.substring(7);
            }else{
                token = header;
            }
            if(redisTokenUtil.isTokenValid(token)){
                redisTokenUtil.deleteToken(token, authExtractUtil.getUserEmail());
            }
        }
        return true;
    }

    private UsersDto refreshToken(Users user) {
        redisTokenUtil.deleteTokenByEmail(user.getEmail());

        String token = jwtUtil.generateToken(user.getId(), user.getName(), user.getEmail(), user.getRole());
        redisTokenUtil.storeToken(token, user.getEmail());
        
        Claims claims = jwtUtil.extractClaims(token);
        return UsersMapper.toBaseDto(user).setToken(token)
            .setIssuedAt(claims.getIssuedAt().toInstant())
            .setExpiresAt(claims.getExpiration().toInstant());
    }
    
}
