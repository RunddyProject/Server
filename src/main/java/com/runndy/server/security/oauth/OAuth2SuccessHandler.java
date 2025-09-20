package com.runndy.server.security.oauth;

import com.runndy.server.security.jwt.JwtTokenProvider;
import com.runndy.server.security.jwt.TokenStore;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

@Slf4j
@Component
@RequiredArgsConstructor
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

  private final JwtTokenProvider jwt;
  private final TokenStore tokenStore;

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

    String redirect = UriComponentsBuilder.fromUriString(req.getRequestURL().toString())
                                          .replacePath("/login/success")
                                          .build()
                                          .toUriString();

    log.info("redirect: {}", redirect);

    getRedirectStrategy().sendRedirect(req, res, redirect);
  }
}