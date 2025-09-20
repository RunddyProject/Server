package com.runndy.server.security.oauth;

import com.runndy.server.security.jwt.JwtTokenProvider;
import com.runndy.server.security.jwt.TokenStore;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;
import java.time.Duration;
import java.time.Instant;
import java.util.Set;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

@Component
@RequiredArgsConstructor
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

  private final JwtTokenProvider jwt;
  private final TokenStore tokenStore;

  // 허용된 클라이언트 origin 목록 (보안을 위해)
  private final Set<String> allowedOrigins = Set.of(
      "http://localhost:5173",
      "https://runddy.co.kr",
      "https://www.runddy.co.kr",
      "https://runddy.lovable.app/"
  );

  @Override
  public void onAuthenticationSuccess(HttpServletRequest req, HttpServletResponse res,
      Authentication auth) throws IOException {
    UserPrincipal user = (UserPrincipal) auth.getPrincipal();

    String sid = "web-" + UUID.randomUUID();                // 디바이스/세션 식별자
    String refresh = jwt.createRefreshToken(user, sid);

    Claims refreshClaims = jwt.parse(refresh).getBody();
    String subject = refreshClaims.getSubject(); // 사용자 식별자 (provider:providerId)
    String jti = refreshClaims.getId(); // 토큰 식별자 -> BlackList
    Duration ttl = Duration.between(Instant.now(), refreshClaims.getExpiration().toInstant());

    // 세션 등록
    tokenStore.registerSession(subject, sid, jti, ttl);

    ResponseCookie cookie = ResponseCookie.from("refreshToken", refresh)
                                          .httpOnly(true)
                                          .secure(true)
                                          .path("/")
                                          .maxAge(ttl)
                                          .sameSite("Lax")
                                          .build();

    res.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());

    // 클라이언트 origin 가져오기
    String clientOrigin = getClientOrigin(req);

    String redirect = UriComponentsBuilder.fromUriString(clientOrigin + "/login/success")
                                          .build()
                                          .toUriString();

    getRedirectStrategy().sendRedirect(req, res, redirect);
  }

  private String getClientOrigin(HttpServletRequest request) {
    // 1. Referer 헤더에서 origin 추출
    String referer = request.getHeader("Referer");
    if (referer != null) {
      try {
        URI refererUri = URI.create(referer);
        String origin = refererUri.getScheme() + "://" + refererUri.getAuthority();
        if (allowedOrigins.contains(origin)) {
          return origin;
        }
      } catch (Exception e) {
        // Referer 파싱 실패시 무시
      }
    }

    // 2. Origin 헤더 확인
    String origin = request.getHeader("Origin");
    if (origin != null && allowedOrigins.contains(origin)) {
      return origin;
    }

    // 3. X-Forwarded-Host 헤더 확인 (프록시 환경)
    String forwardedHost = request.getHeader("X-Forwarded-Host");
    if (forwardedHost != null) {
      String forwardedProto = request.getHeader("X-Forwarded-Proto");
      String protocol = forwardedProto != null ? forwardedProto : "https";
      String constructedOrigin = protocol + "://" + forwardedHost;
      if (allowedOrigins.contains(constructedOrigin)) {
        return constructedOrigin;
      }
    }

    // 4. Host 헤더로 fallback
    String host = request.getHeader("Host");
    if (host != null) {
      String protocol = request.isSecure() ? "https" : "http";
      String constructedOrigin = protocol + "://" + host;
      if (allowedOrigins.contains(constructedOrigin)) {
        return constructedOrigin;
      }
    }

    // 5. 기본값 반환 (모든 방법이 실패한 경우)
    return "http://localhost:5173"; // 또는 설정에서 읽어온 기본 클라이언트 URL
  }
}