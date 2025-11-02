package com.github.donnyk22.project.services.auth;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.donnyk22.project.models.dtos.UsersDto;
import com.github.donnyk22.project.models.entities.Users;
import com.github.donnyk22.project.models.forms.UserLoginForm;
import com.github.donnyk22.project.models.forms.UserRegisterForm;
import com.github.donnyk22.project.models.mappers.UsersMapper;
import com.github.donnyk22.project.repositories.UsersRepository;
import com.github.donnyk22.project.utils.AuthExtractUtil;
import com.github.donnyk22.project.utils.JwtUtil;
import com.github.donnyk22.project.utils.RedisTokenUtil;

import jakarta.servlet.http.HttpServletRequest;

@Service
@Transactional
public class AuthServiceImpl implements AuthService{

    private static final Logger logger = LoggerFactory.getLogger(AuthServiceImpl.class);

    @Autowired UsersRepository usersRepository;
    @Autowired JwtUtil jwtUtil;
    @Autowired RedisTokenUtil redisTokenUtil;
    @Autowired AuthExtractUtil authExtractUtil;

    @Override
    public UsersDto register(UserRegisterForm form) throws Exception {
        if(!form.getPassword().equals(form.getRePassword())){
            logger.error("Retype password doesn't match");
            throw new Exception("Retype password doesn't match. Please try again!");
        }
        Users user = UsersMapper.toEntity(form, new BCryptPasswordEncoder().encode(form.getPassword()));
        if(user == null){
            logger.error("Failed to register a new user");
            throw new Exception("Failed to register a new user. Please try again");
        }
        usersRepository.save(user);
        return UsersMapper.toBaseDto(user);
    }

    @Override
    public UsersDto login(UserLoginForm form) throws Exception {
        Users user = usersRepository.findByEmail(form.getEmail());
        if(user == null){
            logger.error("User not found");
            throw new Exception("User not found");
        }
        Boolean passwordMatch = new BCryptPasswordEncoder().matches(form.getPassword(), user.getPassword());
        if(!passwordMatch){
            logger.error("Invalid password");
            throw new Exception("Invalid email or password");
        }
        String existingToken = redisTokenUtil.getTokenByEmail(user.getEmail());
        if (existingToken != null && redisTokenUtil.isTokenValid(existingToken)) {
            redisTokenUtil.refreshTokenTTL(existingToken, user.getEmail());
            return UsersMapper.toBaseDto(user).setToken(existingToken);
        }
        String token = jwtUtil.generateToken(user.getId(), user.getName(), user.getEmail(), user.getRole());
        redisTokenUtil.storeToken(token, user.getEmail());
        
        return UsersMapper.toBaseDto(user).setToken(token);
    }

    @Override
    public Boolean logout(HttpServletRequest request) throws Exception {
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
    
}
