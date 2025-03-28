package com.jwt.jwttoken.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

import java.security.Key;
import java.util.Date;


@Component
public class JwtService {
    private static final long EXPTIME = 86400000;

    private static final String PREFIX = "TOKEN";

    private static final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    public String getToken(String username) {

        String token = Jwts.builder()
                .setSubject(username)
                .setExpiration(new Date(System.currentTimeMillis() + EXPTIME)).signWith(Key).compact();

        return token;

    }

    public String getAuthUser(HttpServletRequest httpServletRequest) {
        String token = httpServletRequest.getHeader(HttpHeaders.AUTHORIZATION);

        if (token != null) {
            String user = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token.replace(PREFIX, "")).getBody().getSubject();

            if (user != null) {
                return user;
            }

        }
        return null;


    }

}
