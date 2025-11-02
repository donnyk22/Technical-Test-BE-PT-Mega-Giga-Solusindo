package com.github.donnyk22.project.services.supports;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import com.github.donnyk22.project.utils.AuthExtractUtil;

@Service
public class SupportsServiceImpl implements SupportsService{

    @Autowired StringRedisTemplate redisTemplate;
    @Autowired AuthExtractUtil authExtractUtil;

    @Override
    public String redisCheckConnection() {
       try {
            String key = "redis_test_key";
            redisTemplate.opsForValue().set(key, "ok", 5, TimeUnit.SECONDS);
            String value = redisTemplate.opsForValue().get(key);
            if("ok".equals(value)){
                return "Redis connected";
            }
            return "Redis not connected";
        } catch (Exception e) {
            return "Error to connect Redis: " + e.getMessage();
        }
    }

    @Override
    public Map<String, String> checkUserLoginCredential() {
        Map<String, String> result = new HashMap<>();
        result.put("id", String.valueOf(authExtractUtil.getUserId()));
        result.put("name", authExtractUtil.getUserName());
        result.put("email", authExtractUtil.getUserEmail());
        result.put("role", authExtractUtil.getUserRole());
        return result;
    }
    
}
