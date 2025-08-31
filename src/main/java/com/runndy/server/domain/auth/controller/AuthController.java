package com.runndy.server.domain.auth.controller;

import com.runndy.server.domain.auth.service.AuthService;
import java.util.Map;
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

  @PreAuthorize("isAuthenticated()")
  @PostMapping("/refresh")
  public ResponseEntity<Map<String, String>> refresh(@CookieValue("refreshToken") String refresh) {

    // {accessToken, cookie}
    Map<String, Object> refreshResult = authService.refreshToken(refresh);

    return ResponseEntity.ok()
                         .header(HttpHeaders.SET_COOKIE, refreshResult.get("cookie").toString())
                         .body(Map.of("accessToken", refreshResult.get("accessToken").toString()));
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

