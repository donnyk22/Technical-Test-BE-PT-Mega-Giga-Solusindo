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

    //uses same token will update the data
    public void store(String bucket, String token, String value, Integer ttl, TimeUnit unit) {
        redis.opsForValue().set(bucket + ":" + token, value, ttl, unit);
    }

    public String get(String bucket, String token) {
        return redis.opsForValue().get(bucket + ":" + token);
    }

    public void updateKeepTTL(String bucket, String token, String newValue) {
        Long ttl = redis.getExpire(bucket + ":" + token, TimeUnit.SECONDS);

        if (ttl != null && ttl > 0) {
            redis.opsForValue().set(bucket + ":" + token, newValue, ttl, TimeUnit.SECONDS);
        }
    }

    public void delete(String bucket, String token) {
        redis.delete(bucket + ":" + token);
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

    public void deleteToken(String token, String email) {
        redis.delete("token:" + token);
        redis.delete("user:" + email);
    }

    public void deleteTokenByEmail(String email) {
        String token = getTokenByEmail(email);
        if (token != null) {
            deleteToken(token, email);
        }
    }
    
}
