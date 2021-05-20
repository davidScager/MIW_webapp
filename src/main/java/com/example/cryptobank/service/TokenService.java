package com.example.cryptobank.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Base64;

/*
@auth HvS
 */

@Service
public class TokenService {
    private Logger logger = LoggerFactory.getLogger(TokenService.class);
    private Instant now = Instant.now();
    private byte[] secret = Base64.getDecoder().decode("wf397i3th324m94j32832miuvuiububzduvabiovasrvnesonoe7632bfvn");

    public TokenService() {
        super();
    }

    public String generateJwtToken(String email, String subject, int minutesValid) {
        String jwt = Jwts.builder()
                .setSubject(subject)
                .setId(email)
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(now.plus(minutesValid, ChronoUnit.MINUTES)))
                .signWith(Keys.hmacShaKeyFor(secret))
                .compact();
        logger.info(jwt);
        return jwt;
    }


    public String parseToken(String token, String subject) {

        Jws<Claims> result = Jwts.parserBuilder()
                .requireSubject(subject)
                .setAllowedClockSkewSeconds(10)
                .setSigningKey(Keys.hmacShaKeyFor(secret))
                .build().parseClaimsJws(token);
        String id = result.getBody().getId();
        logger.info(id);
        return id;
    }
}
