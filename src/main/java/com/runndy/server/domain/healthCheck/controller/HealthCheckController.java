package com.runndy.server.domain.healthCheck.controller;

import com.runndy.server.domain.healthCheck.api.HealthCheckApi;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
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
  public ResponseEntity<String> healthCheckWithAuth() {
    return ResponseEntity.ok("auth pong");
  }
}
