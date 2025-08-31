package com.runndy.server.domain.auth.service;

import com.runndy.server.security.jwt.JwtTokenProvider;
import com.runndy.server.security.jwt.TokenStore;
import com.runndy.server.security.oauth.UserPrincipal;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

  private final JwtTokenProvider jwt;
  private final TokenStore tokenStore;

  /**
   * 리프레시 토큰으로 액세스 토큰 재발급
   * @param refresh
   * @return
   */
  public Map<String, Object> refreshToken(String refresh) {
    Jws<Claims> jws = jwt.parse(refresh);
    Claims c = jws.getBody();
    String subject = c.getSubject();
    String jti = c.getId();
    if (!tokenStore.existsRefresh(subject, jti)) {
      // TODO: throw 401 err
      return null;
    }

    // 새 토큰 발급 및 로테이션
    UserPrincipal principal = new UserPrincipal(null, subject.split(":")[0], subject.split(":")[1],
        null, subject, List.of(new SimpleGrantedAuthority("ROLE_USER")));
    String newAccess = jwt.createAccessToken(principal);
    String newRefresh = jwt.createRefreshToken(principal);

    // 기존 리프레시 효화, 새 리프레시 저장
    tokenStore.revokeAllRefresh(subject);
    Claims rc = jwt.parse(newRefresh).getBody();
    tokenStore.saveRefresh(subject, rc.getId(),
        Duration.between(Instant.now(), rc.getExpiration().toInstant()));

    ResponseCookie cookie = ResponseCookie.from("refreshToken", newRefresh)
                                          .httpOnly(true).secure(true).path("/").maxAge(
            Duration.between(Instant.now(), rc.getExpiration().toInstant())).sameSite("Lax")
                                          .build();

    return Map.of("accessToken", newAccess, "cookie", cookie);
  }

  public ResponseCookie logout(Optional<String> authHeader, String refresh) {
    authHeader.filter(h -> h.startsWith("Bearer ")).ifPresent(h -> {
      Claims c = jwt.parse(h.substring(7)).getBody();
      Duration ttl = Duration.between(Instant.now(), c.getExpiration().toInstant());
      tokenStore.blacklist(c.getId(), ttl);
    });
    if (refresh != null) {
      Claims rc = jwt.parse(refresh).getBody();
      String subject = rc.getSubject();
      tokenStore.revokeAllRefresh(subject);
    }
    return ResponseCookie.from("refreshToken", "").httpOnly(true).secure(true)
                         .path("/").maxAge(0).build();
  }
}
