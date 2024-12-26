package com.athosfs.todosimple.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.util.Date;
import java.util.Objects;
import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JWTUtil {

  @Value("${jwt.secret}")
  private String secret;

  @Value("${jwt.expiration}")
  private Long expiration;

  public String generateToken(String username) {
    return Jwts.builder()
        .setSubject(username)
        .setIssuedAt(new Date())
        .setExpiration(new Date(System.currentTimeMillis() + this.expiration))
        .signWith(this.getKeyBySecret())
        .compact();
  }

  private SecretKey getKeyBySecret() {
    return Keys.hmacShaKeyFor(this.secret.getBytes());
  }

  public boolean isValidToken(String token) {
    Claims claims = getClaims(token);
    if (Objects.nonNull(claims)) {
      String username = claims.getSubject();
      Date expirationDate = claims.getExpiration();
      Date now = new Date(System.currentTimeMillis());
      if (Objects.nonNull(username)
          && Objects.nonNull(expirationDate)
          && now.before(expirationDate)) {
        return true;
      }
    }
    return false;
  }

  private Claims getClaims(String token) {
    SecretKey key = this.getKeyBySecret();
    try{
      return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
    } catch (Exception e) {
      return null;
    }
  }
}
