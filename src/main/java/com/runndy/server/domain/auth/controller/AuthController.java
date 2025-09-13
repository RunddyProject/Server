package com.runndy.server.domain.auth.controller;

import com.runndy.server.domain.auth.controller.api.AuthApi;
import com.runndy.server.domain.auth.controller.dto.CreateAccessTokenResponseDto;
import com.runndy.server.domain.auth.service.AuthService;
import com.runndy.server.domain.auth.service.dto.TokenDto;

import io.swagger.v3.oas.annotations.Hidden;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController implements AuthApi {

  private final AuthService authService;

  /**
   * Access token 재발급
   */
  @PostMapping("/access-token")
  public ResponseEntity<CreateAccessTokenResponseDto> refresh(
      @CookieValue("refreshToken") String refreshToken) {

    TokenDto tokenDto = authService.refreshToken(refreshToken);

    ResponseCookie cookie = ResponseCookie.from("refreshToken", tokenDto.getRefreshToken())
                                          .httpOnly(true)
                                          .secure(true)
                                          .path("/")
                                          .maxAge(tokenDto.getTtl())
                                          .sameSite("Lax")
                                          .build();

    return ResponseEntity.ok()
                         .header(HttpHeaders.SET_COOKIE, cookie.toString())
                         .body(CreateAccessTokenResponseDto.of(
                             tokenDto.getAccessToken()));
  }

  /**
   * Logout
   */
  @PreAuthorize("isAuthenticated()")
  @PostMapping("/logout")
  public ResponseEntity<Void> logout(
      @RequestHeader(HttpHeaders.AUTHORIZATION) Optional<String> authHeader,
      @CookieValue(value = "refreshToken") String refresh) {

    authService.logout(authHeader, refresh);
    ResponseCookie expiredCookie = ResponseCookie.from("refreshToken", "")
                                                 .httpOnly(true)
                                                 .secure(true)
                                                 .sameSite("Lax")
                                                 .path("/")
                                                 .maxAge(0)
                                                 .build();

    return ResponseEntity.noContent()
                         .header(HttpHeaders.SET_COOKIE, expiredCookie.toString())
                         .build();
  }
}

