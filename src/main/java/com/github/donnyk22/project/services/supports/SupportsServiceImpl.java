package com.github.donnyk22.project.services.supports;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;

import com.github.donnyk22.project.utils.AuthExtractUtil;
import com.github.donnyk22.project.utils.JwtUtil;
import com.github.donnyk22.project.utils.RedisTokenUtil;

import io.jsonwebtoken.Claims;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class SupportsServiceImpl implements SupportsService {

    private final StringRedisTemplate redisTemplate;
    private final AuthExtractUtil authExtractUtil;
    private final RedisTokenUtil redisTokenUtil;
    private final JwtUtil jwtUtil;
    private final WebApplicationContext webApplicationContext;

    @Override
    public String redisCheckConnection() {
        try {
            String key = "redis_test_key_" + UUID.randomUUID().toString();
            redisTemplate.opsForValue().set(key, "ok", 5, TimeUnit.SECONDS);
            String value = redisTemplate.opsForValue().get(key);
            if ("ok".equals(value)) {
                return "Redis connected";
            }
            return "Redis not connected";
        } catch (Exception e) {
            return "Error to connect Redis: " + e.getMessage();
        }
    }

    @Override
    public Map<String, Object> checkUserLoginCredential() {
        String token = redisTokenUtil.getTokenByEmail(authExtractUtil.getUserEmail());
        Claims claims = jwtUtil.extractClaims(token);

        Map<String, Object> result = new HashMap<>();
        result.put("id", authExtractUtil.getUserId());
        result.put("name", authExtractUtil.getUserName());
        result.put("email", authExtractUtil.getUserEmail());
        result.put("role", authExtractUtil.getUserRole());
        result.put("token", redisTokenUtil.getTokenByEmail(authExtractUtil.getUserEmail()));
        result.put("issuedAt", claims.getIssuedAt().toInstant());
        result.put("expiresAt", claims.getExpiration().toInstant());
        return result;
    }

    @Override
    public List<String> getBeanList() {
        return Arrays.stream(
            webApplicationContext.getBeanNamesForType(Object.class)
        )
        .sorted()
        .toList();
    }
}
