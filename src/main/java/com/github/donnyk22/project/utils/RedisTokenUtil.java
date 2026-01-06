package com.github.donnyk22.project.utils;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import com.github.donnyk22.project.models.dtos.AppProperties;

@Component
public class RedisTokenUtil {

    @Autowired StringRedisTemplate redis;

    private final long TTL_MINUTES;

    public RedisTokenUtil(AppProperties appProperties){
        this.TTL_MINUTES = appProperties.getTtlMinutes();
    }

    public void storeToken(String token, String email) {
        redis.opsForValue().set("token:" + token, email, TTL_MINUTES, TimeUnit.MINUTES);
        redis.opsForValue().set("user:" + email, token, TTL_MINUTES, TimeUnit.MINUTES);
    }

    public Boolean isTokenValid(String token) {
        return redis.opsForValue().get("token:" + token) != null;
    }

    public String getTokenByEmail(String email) {
        return redis.opsForValue().get("user:" + email);
    }

    public void refreshTokenTTL(String token, String email) {
        redis.expire("token:" + token, TTL_MINUTES, TimeUnit.MINUTES);
        redis.expire("user:" + email, TTL_MINUTES, TimeUnit.MINUTES);
    }

    public void deleteToken(String token, String email) {
        redis.delete("token:" + token);
        redis.delete("user:" + email);
    }
    
}
