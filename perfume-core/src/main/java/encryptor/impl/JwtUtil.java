package encryptor.impl;

import io.jsonwebtoken.Jwts;

import java.time.LocalDateTime;
import java.util.Date;

public class JwtUtil {

    private final String secret;

    public JwtUtil(String secret) {
        this.secret = secret;
    }

    public String create(String payload, LocalDateTime now) {
        return Jwts
                .builder()
                .setSubject(payload)
                .setIssuedAt(toDate(now))
                .setExpiration(toDate(now.plusHours(2)))
                .signWith(io.jsonwebtoken.SignatureAlgorithm.HS512, secret)
                .compact();
    }

    private Date toDate(LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(java.time.ZoneId.systemDefault()).toInstant());
    }
}
