package com.example.eventify.Kernel;

import com.example.eventify.Entities.User;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.List;

@Component
public class JwtUtils {
    private final String SECRET_KEY = "OfjJUPUVgPGXL5sAsgzxk3qFG7j3e83JdCGG1yBklLu1XY61WCJ9C8CjWfQUmtQQc5crLW1E7sUegE5L8R4zta314ZFGbgT5EieJV2uiValSF3oDIJ0bNRgMDk+ovQxcme87ppV69cWywTEHyxmiHHKAxDBWd9WMUTFfxILI6fY=";
    private final long EXPIRATION_TIME = 7_200_000; // 2 hours
    private final JwtParser jwtParser;
    private final String TOKEN_HEADER = "Authorization";
    private final String TOKEN_PREFIX = "Bearer";

    public JwtUtils(){
        this.jwtParser = Jwts
                .parserBuilder()
                .setSigningKey(GetSignatureKey())
                .build();
    }

    public String GenerateToken(User user){
        Claims claims = Jwts.claims().setSubject(user.getEmail());
        claims.put("username", user.getUsername());
        claims.put("userId", user.getId());
        Date tokenCreation = new Date();
        Date tokenValidity = new Date(System.currentTimeMillis() + EXPIRATION_TIME);

        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(tokenValidity)
                .signWith(GetSignatureKey(), SignatureAlgorithm.HS512)
                .compact();
    }

    public Claims ResolveClaims(HttpServletRequest req){
        try {
            String token = ResolveToken(req);
            if (token != null) {
                return ParseJwtClaims(token);
            }
            return null;
        } catch (ExpiredJwtException ex) {
            req.setAttribute("expired", ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            req.setAttribute("invalid", ex.getMessage());
            throw ex;
        }
    }

    public String ResolveToken(HttpServletRequest request) {
        String authHeader = request.getHeader(TOKEN_HEADER);
        if (authHeader != null && authHeader.matches("^Bearer (.*)$")) {
            return authHeader.substring(7);
        }

        return null;
    }

    public boolean ValidateClaims(Claims claims) throws AuthenticationException {
        try {
            return claims.getExpiration().after(new Date());
        } catch (Exception e) {
            throw e;
        }
    }

    public String GetEmail(Claims claims) {
        return claims.getSubject();
    }

    private List<String> GetRoles(Claims claims) {
        return (List<String>) claims.get("roles");
    }

    private Key GetSignatureKey(){
        byte[] keyBytes = SECRET_KEY.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    private Claims ParseJwtClaims(String token) {
        return jwtParser.parseClaimsJws(token).getBody();
    }
}
