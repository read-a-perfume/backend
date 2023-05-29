package jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class JsonWebTokenGenerator {

    private final Key secret;

    public JsonWebTokenGenerator(String secret) {
        assert secret != null;
        assert !secret.isEmpty();
        assert secret.length() >= 32;

        this.secret = Keys.hmacShaKeyFor(secret.getBytes());
    }

    public String create(String subject, Map<String, Object> claims, int expirationSeconds, LocalDateTime now) {
        assert subject != null;
        assert !subject.isEmpty();
        assert expirationSeconds > 60 * 60;
        assert claims != null;

        return Jwts
                .builder()
                .signWith(secret, SignatureAlgorithm.HS256)
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(toDate(now))
                .setExpiration(toDate(now.plusSeconds(expirationSeconds)))
                .compact();
    }

    public boolean isExpired(String jwt, LocalDateTime now) {
        assert jwt != null;
        assert !jwt.isEmpty();

        return Jwts
                .parserBuilder()
                .setSigningKey(secret)
                .build()
                .parseClaimsJws(jwt)
                .getBody()
                .getExpiration()
                .before(toDate(now));
    }

    public String getSubject(String jwt) {
        assert jwt != null;
        assert !jwt.isEmpty();

        return Jwts
                .parserBuilder()
                .setSigningKey(secret)
                .build()
                .parseClaimsJws(jwt)
                .getBody()
                .getSubject();
    }

    public <T> T getClaim(String jwt, String key, Class<T> requiredType) {
        assert jwt != null;
        assert !jwt.isEmpty();
        assert key != null;
        assert !key.isEmpty();

        return Jwts
                .parserBuilder()
                .setSigningKey(secret)
                .build()
                .parseClaimsJws(jwt)
                .getBody()
                .get(key, requiredType);
    }

    public Boolean verify(String jwt, LocalDateTime now) {
        assert jwt != null;
        assert !jwt.isEmpty();

        try {
            Jwts
                    .parserBuilder()
                    .setSigningKey(secret)
                    .build()
                    .parseClaimsJws(jwt);
            return !this.isExpired(jwt, now);
        } catch (Exception e) {
            return false;
        }
    }

    private Date toDate(LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(java.time.ZoneId.systemDefault()).toInstant());
    }
}
