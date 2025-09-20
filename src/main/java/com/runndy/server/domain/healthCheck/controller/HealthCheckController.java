package com.runndy.server.domain.healthCheck.controller;

import com.runndy.server.domain.healthCheck.controller.api.HealthCheckApi;
import com.runndy.server.domain.healthCheck.controller.dto.SelectLoginUserResponseDto;
import com.runndy.server.domain.user.service.dto.LoginUserInfoDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/health")
public class HealthCheckController implements HealthCheckApi {

  @GetMapping
  public ResponseEntity<String> healthCheck() {
    return ResponseEntity.ok("pong");
  }

  @PreAuthorize("isAuthenticated()")
  @GetMapping("/auth")
  public ResponseEntity<SelectLoginUserResponseDto> healthCheckWithAuth(
      @AuthenticationPrincipal LoginUserInfoDto loginUserInfoDto) {
    SelectLoginUserResponseDto response = SelectLoginUserResponseDto.from(loginUserInfoDto);
    return ResponseEntity.ok()
                         .body(response);
  }

  @PreAuthorize("isAuthenticated()")
  @PostMapping("/post")
  public ResponseEntity<SelectLoginUserResponseDto> postHealthCheckWithAuth(
      @AuthenticationPrincipal LoginUserInfoDto loginUserInfoDto) {
    SelectLoginUserResponseDto response = SelectLoginUserResponseDto.from(loginUserInfoDto);
    return ResponseEntity.ok()
                         .body(response);
  }
}
