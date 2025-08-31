package com.runndy.server.security.jwt;

import com.runndy.server.security.oauth.UserPrincipal;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.time.Instant;
import java.util.Base64;
import java.util.Date;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

@Component
public class JwtTokenProvider {

  @Value("${jwt.secret}")
  private String secret;
  @Value("${jwt.issuer}")
  private String issuer;
  @Value("${jwt.access-token-seconds}")
  private long accessSecs;
  @Value("${jwt.refresh-token-seconds}")
  private long refreshSecs;

  private Key key() {
    return Keys.hmacShaKeyFor(Base64.getDecoder().decode(secret));
  }

  public String createAccessToken(UserPrincipal user) {
    Instant now = Instant.now();
    return Jwts.builder()
               .setIssuer(issuer)
               .setSubject(user.getName()) // provider:providerId
               .claim("uid", user.getName())
               .claim("roles",
                   user.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList())
               .setIssuedAt(Date.from(now))
               .setExpiration(Date.from(now.plusSeconds(accessSecs)))
               .setId(UUID.randomUUID().toString()) // jti
               .signWith(key(), SignatureAlgorithm.HS256)
               .compact();
  }

  public String createRefreshToken(UserPrincipal user) {
    Instant now = Instant.now();
    return Jwts.builder()
               .setIssuer(issuer)
               .setSubject(user.getName())
               .setIssuedAt(Date.from(now))
               .setExpiration(Date.from(now.plusSeconds(refreshSecs)))
               .setId(UUID.randomUUID().toString())
               .signWith(key(), SignatureAlgorithm.HS256)
               .compact();
  }

  public Jws<Claims> parse(String token) {
    return Jwts.parserBuilder().setSigningKey(key()).build().parseClaimsJws(token);
  }
}

