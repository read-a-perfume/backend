package encryptor.impl;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class JwtUtil {

    private static final String TOKEN_PREFIX = "Bearer ";
    private static final SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS512);

    public String create(String subject, List<String> roles, LocalDateTime now) {
        Map<String, Object> claims = new HashMap<>();

        claims.put("username", subject);
        claims.put("roles", roles);


        String token = Jwts
                .builder()
                .setSubject(subject)
                .setIssuedAt(toDate(now))
                .setClaims(claims)
                .setExpiration(toDate(now.plusHours(2)))
                .signWith(key)
                .compact();
        return TOKEN_PREFIX + token;
    }

    public String getUsername(String token){
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .get("username", String.class);
    }

    public boolean expirationToken(String token)  {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody().getExpiration();
            return true;
        } catch (ExpiredJwtException ex) {
            return false;
        }
    }

    private Date toDate(LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(java.time.ZoneId.systemDefault()).toInstant());
    }

    public String getToken(String header) {
        if (header == null || !header.startsWith(TOKEN_PREFIX)) {
            return null;
        }
        return header.replace(TOKEN_PREFIX, "");
    }
}
