package com.example.cryptobank.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
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

    private Instant now = Instant.now();
    private byte[] secret = Base64.getDecoder().decode("wf397i3th324m94j32832miuvuiububzduvabiovasrvnesonoe7632bfvn");

    public TokenService() {
        super();
    }

    public String getToken() {
        String jwt = Jwts.builder()
                .setSubject("authentication")
                .setAudience("djvbd")
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(now.plus(1, ChronoUnit.HOURS)))
                .signWith(Keys.hmacShaKeyFor(secret))
                .compact();
        return jwt;
    }

    public void parseToken(String token) {

        Jws<Claims> result = Jwts.parserBuilder()
                .requireAudience("")
                .setAllowedClockSkewSeconds(10)
                .setSigningKey(Keys.hmacShaKeyFor(secret))
                .build().parseClaimsJws(token);

    }
}
