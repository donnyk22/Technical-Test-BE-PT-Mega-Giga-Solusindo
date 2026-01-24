package com.github.donnyk22.project.configurations;

import java.io.IOException;
import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.donnyk22.project.models.dtos.ApiResponse;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;

@Component
public class RateLimitFilter extends OncePerRequestFilter {

    private final Map<String, Bucket> buckets = new ConcurrentHashMap<>();

    private static final int MAX_REQUESTS = 50;
    private static final Duration MAX_REQ_DURATION = Duration.ofMinutes(1);

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String ip = request.getRemoteAddr();
        Bucket bucket = buckets.computeIfAbsent(ip, this::createBucket);

        if (bucket.tryConsume(1)) {
            filterChain.doFilter(request, response);
        } else {
            sendTooManyReqResponse(response, "Too Many Requests");
        }
    }

    private Bucket createBucket(String key) {
    Bandwidth limit = Bandwidth.builder()
        .capacity(MAX_REQUESTS)
        .refillIntervally(MAX_REQUESTS, MAX_REQ_DURATION)
        .build();

        return Bucket.builder()
            .addLimit(limit)
            .build();
    }

    private void sendTooManyReqResponse(HttpServletResponse res, String errorMessage) throws IOException {
        res.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
        res.setContentType("application/json");
        ApiResponse<Object> response = new ApiResponse<>(
            HttpStatus.TOO_MANY_REQUESTS.value(),
            errorMessage,
            null
        );
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(response);
        res.getWriter().write(json);
    }

    // Uncomment the following method to apply rate limiting only to specific URL patterns
    // @Override
    // protected boolean shouldNotFilter(HttpServletRequest request) {
    //     return !request.getRequestURI().startsWith("/api");
    // }

}
