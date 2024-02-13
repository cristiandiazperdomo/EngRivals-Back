package web.app.engrivals.engrivals.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SecureDigestAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {
  @Value("${jwt.secret}")
  private String secretKey;
  @Value("${jwt.expirationTime}")
  private Long jwtExpirationTime;

  public String generateToken(UserDetails user) {
    Map<String, Object> extraClaims = new HashMap<>();
    return Jwts.builder()
            .claims(extraClaims)
            .subject(user.getUsername())
            .issuedAt(new Date(System.currentTimeMillis()))
            .expiration(Date.from(Instant.now().plus(jwtExpirationTime, ChronoUnit.MILLIS)))
            .signWith(getKey(), Jwts.SIG.HS256)
            .compact();
  }

  private SecretKey getKey() {
    byte[] keyBites = Decoders.BASE64.decode(secretKey);
    return Keys.hmacShaKeyFor(keyBites);
  }

  private Claims extractAllClaims(String token) {
    return Jwts.parser()
            .verifyWith(getKey())
            .build()
            .parseSignedClaims(token)
            .getPayload();
  }

  private <T> T getClaim(String token, Function<Claims, T> claimsResolver) {
    final Claims claims = extractAllClaims(token);
    return claimsResolver.apply(claims);
  }

  private Date getExpiration(String token) {
    return getClaim(token, Claims::getExpiration);
  }

  private boolean isTokenExpired(String token) {
    return getExpiration(token).before(new Date());
  }

  public String getUsernameFromToken(String token) {
    return getClaim(token, Claims::getSubject);
  }

  public boolean isTokenValid(String token, UserDetails userDetails) {
    final String email = getUsernameFromToken(token);
    return (email.equals(userDetails.getUsername()) &&
            !isTokenExpired(token));
  }
}
