package com.runndy.server.security.oauth;

import com.runndy.server.security.jwt.JwtTokenProvider;
import com.runndy.server.security.jwt.TokenStore;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

@Component
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
  private final JwtTokenProvider jwt;
  private final TokenStore tokenStore;

  public OAuth2SuccessHandler(JwtTokenProvider jwt, TokenStore tokenStore) {
    this.jwt = jwt; this.tokenStore = tokenStore;
  }

  @Override
  public void onAuthenticationSuccess(HttpServletRequest req, HttpServletResponse res, Authentication auth) throws IOException {
    UserPrincipal user = (UserPrincipal) auth.getPrincipal();

    String access = jwt.createAccessToken(user);
    String refresh = jwt.createRefreshToken(user);

    Claims refreshClaims = jwt.parse(refresh).getBody();
    String subject = refreshClaims.getSubject(); // 사용자 식별자 (provider:providerId)
    String jti = refreshClaims.getId(); // 토큰 식별자 -> BlackList
    Duration ttl = Duration.between(Instant.now(), refreshClaims.getExpiration().toInstant());
    tokenStore.saveRefresh(subject, jti, ttl);

    ResponseCookie cookie = ResponseCookie.from("refreshToken", refresh)
                                          .httpOnly(true)
                                          .secure(true)
                                          .path("/")
                                          .maxAge(ttl)
                                          .sameSite("Lax")
                                          .build();

    res.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());

    // TODO: Client로 redirect
    String redirect = UriComponentsBuilder.fromUriString("/login/success")
                                          .queryParam("accessToken", access).build().toUriString();

    getRedirectStrategy().sendRedirect(req, res, redirect);
  }
}
