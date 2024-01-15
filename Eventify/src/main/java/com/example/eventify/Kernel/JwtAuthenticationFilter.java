package com.example.eventify.Kernel;

import com.example.eventify.Entities.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.Console;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final ObjectMapper mapper;
    public JwtAuthenticationFilter(ObjectMapper mapper){
        this.mapper = mapper;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        Map<String, Object> errorDetails = new HashMap<>();
        try {
            String accessToken = JwtUtils.ResolveToken(request);

            if (accessToken == null) {
                filterChain.doFilter(request, response);
                return;
            }

            Claims claims = JwtUtils.ResolveClaims(request);

            if (claims != null & JwtUtils.ValidateClaims(claims)) {
                String username = claims.get("username").toString();
                Authentication authentication = new UsernamePasswordAuthenticationToken(username, "", new ArrayList<>());
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (Exception ex) {
            errorDetails.put("message", "Authentication Error");
            errorDetails.put("details", ex.getMessage());
            response.setStatus(HttpStatus.FORBIDDEN.value());
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);

            mapper.writeValue(response.getWriter(), errorDetails);
        }

        filterChain.doFilter(request, response);
    }
}
