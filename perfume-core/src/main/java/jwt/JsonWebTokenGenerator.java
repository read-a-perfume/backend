package jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Map;
import javax.crypto.SecretKey;
import org.springframework.stereotype.Component;

@Component
public class JsonWebTokenGenerator {

  private final Key secret;

  public JsonWebTokenGenerator(String secret) {
    assert secret != null;
    assert !secret.isEmpty();
    assert secret.length() >= 32;

    this.secret = Keys.hmacShaKeyFor(secret.getBytes());
  }

  public String create(
      String subject, Map<String, Object> claims, int expirationSeconds, LocalDateTime now) {
    assert subject != null;
    assert !subject.isEmpty();
    assert expirationSeconds > 0;
    assert claims != null;

    return Jwts.builder()
        .signWith(secret)
        .claims(claims)
        .subject(subject)
        .issuedAt(toDate(now))
        .expiration(toDate(now.plusSeconds(expirationSeconds)))
        .compact();
  }

  public boolean isExpired(String jwt, LocalDateTime now) {
    assert jwt != null;
    assert !jwt.isEmpty();

    return Jwts.parser()
        .verifyWith((SecretKey) secret)
        .build()
        .parseSignedClaims(jwt)
        .getPayload()
        .getExpiration()
        .before(toDate(now));
  }

  public String getSubject(String jwt) {
    assert jwt != null;
    assert !jwt.isEmpty();

    return Jwts.parser()
        .verifyWith((SecretKey) secret)
        .build()
        .parseSignedClaims(jwt)
        .getPayload()
        .getSubject();
  }

  public <T> T getClaim(String jwt, String key, Class<T> requiredType) {
    assert jwt != null;
    assert !jwt.isEmpty();
    assert key != null;
    assert !key.isEmpty();

    return Jwts.parser()
        .verifyWith((SecretKey) secret)
        .build()
        .parseSignedClaims(jwt)
        .getPayload()
        .get(key, requiredType);
  }

  public boolean verify(String jwt, LocalDateTime now) {
    assert jwt != null;
    assert !jwt.isEmpty();

    try {
      Jwts.parser().verifyWith((SecretKey) secret).build().parseSignedClaims(jwt);
      return !this.isExpired(jwt, now);
    } catch (Exception e) {
      return false;
    }
  }

  private Date toDate(LocalDateTime localDateTime) {
    return Date.from(localDateTime.atZone(java.time.ZoneId.systemDefault()).toInstant());
  }
}
