package com.emp.employeeManagement.ratelimit;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Refill;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class RateLimitingFilter implements Filter {
    private final Map<String, Bucket> cache = new ConcurrentHashMap<>();

    private Bucket createNewBucket(String path){
        Bandwidth limit;
        if(path.startsWith("/employeeById")){
             limit= Bandwidth.classic(5, Refill.intervally(5, Duration.ofMinutes(1)));
        }
        else{
             limit= Bandwidth.classic(10, Refill.intervally(10, Duration.ofMinutes(1)));
        }
        return Bucket.builder().addLimit(limit).build();
    }
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest httpRequest= (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        String ip = httpRequest.getRemoteAddr();
        String path = httpRequest.getRequestURI();
        String cacheKey = ip + ":" + path;
        Bucket bucket= cache.computeIfAbsent(cacheKey, k->createNewBucket(path) );
        if(bucket.tryConsume(1)){
            chain.doFilter(request,response);
        }else{
            httpResponse.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
            httpResponse.setContentType("application/json");
            httpResponse.getWriter().write("{\"error\": \"Too many requests. Please try again later.\"}");
        }

    }
}
