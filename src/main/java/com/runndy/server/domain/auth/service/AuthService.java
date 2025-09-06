package com.runndy.server.domain.auth.service;

import com.runndy.server.domain.auth.service.dto.TokenDto;
import com.runndy.server.domain.user.enums.SocialType;
import com.runndy.server.error.CustomException;
import com.runndy.server.security.jwt.JwtTokenProvider;
import com.runndy.server.security.jwt.TokenStore;
import com.runndy.server.security.oauth.UserPrincipal;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

  private final JwtTokenProvider jwt;
  private final TokenStore tokenStore;

  public TokenDto refreshToken(String refresh) {
    Jws<Claims> jws = jwt.parse(refresh);
    Claims c = jws.getBody();
    String subject = c.getSubject();
    String sid = (String) c.get("sid");
    String jti = c.getId();

    // 현재 세션의 유효 리프레시인지 확인 (재사용 방지)
    if (!tokenStore.isCurrentRefresh(subject, sid, jti)) {
      tokenStore.revokeSession(subject, sid);
      throw new CustomException(HttpStatus.UNAUTHORIZED, "Invalid refresh token");
    }

    // 새 토큰 발급 (sid 유지)
    SocialType socialType = SocialType.valueOf(subject.split(":")[0]);

    UserPrincipal principal = new UserPrincipal(null, socialType, subject.split(":")[1],
        null, subject, List.of(new SimpleGrantedAuthority("ROLE_USER")));
    String newAccess = jwt.createAccessToken(principal);
    String newRefresh = jwt.createRefreshToken(principal, sid);

    // 회전: 새 jti로 교체
    Claims refreshClaims = jwt.parse(newRefresh).getBody();
    Duration ttl = Duration.between(Instant.now(), refreshClaims.getExpiration().toInstant());
    tokenStore.rotateRefresh(subject, sid, refreshClaims.getId(), ttl); // 새로운 jti로 교체

    return TokenDto.of(newAccess, newRefresh, ttl);
  }

  public void logout(Optional<String> authHeader, String refresh) {
    // 1) 액세스 블랙리스트
    authHeader.filter(h -> h.startsWith("Bearer "))
              .ifPresent(h -> {
                Claims ac = jwt.parse(h.substring(7)).getBody();
                Duration ttl = Duration.between(Instant.now(), ac.getExpiration().toInstant());

                tokenStore.blacklistAccess(ac.getId(), ttl);
              });

    // 2) 세션 종료
    if (refresh != null) {
      Claims rc = jwt.parse(refresh).getBody();
      tokenStore.revokeSession(rc.getSubject(), (String) rc.get("sid"));
    }
  }
}
