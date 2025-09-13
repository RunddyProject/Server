package com.runndy.server.domain.healthCheck.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

@Tag(name = "Health Check", description = "Health Check API")
public interface HealthCheckApi {


  @Operation(summary = "health check", description = "서버의 상태를 확인합니다.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "정상 처리"),
  })
  ResponseEntity<String> healthCheck();

  @Operation(summary = "로그인 health check", description = "로그인 상태에서 서버의 상태를 확인합니다.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "정상 처리"),
      @ApiResponse(responseCode = "401", description = "로그인 필요"),
  })
  ResponseEntity<String> healthCheckWithAuth();
}
