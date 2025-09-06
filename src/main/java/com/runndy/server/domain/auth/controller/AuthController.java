package com.runndy.server.domain.auth.controller;

import com.runndy.server.domain.auth.controller.dto.CreateAccessTokenResponseDto;
import com.runndy.server.domain.auth.service.AuthService;
import com.runndy.server.domain.auth.service.dto.TokenDto;

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
public class AuthController {

  private final AuthService authService;

  /**
   * Access token 재발급
   */
  @PreAuthorize("isAuthenticated()")
  @PostMapping("/access-token")
  public ResponseEntity<CreateAccessTokenResponseDto> refresh(
      @CookieValue("refreshToken") String refresh) {

    TokenDto tokenDto = authService.refreshToken(refresh);

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

  @PostMapping("/logout")
  public ResponseEntity<Void> logout(
      @RequestHeader(HttpHeaders.AUTHORIZATION) Optional<String> authHeader,
      @CookieValue(value = "refreshToken", required = false) String refresh) {

    ResponseCookie expiredCookie = authService.logout(authHeader, refresh);

    return ResponseEntity.noContent().header(HttpHeaders.SET_COOKIE, expiredCookie.toString())
                         .build();
  }
}

