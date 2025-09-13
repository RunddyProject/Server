package com.runndy.server.domain.healthCheck.controller.api;

import com.runndy.server.domain.healthCheck.controller.dto.SelectLoginUserResponseDto;
import com.runndy.server.domain.user.service.dto.LoginUserInfoDto;
import com.runndy.server.error.ErrorResponse;
import com.runndy.server.security.oauth.UserPrincipal;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

@Tag(name = "Health Check", description = "Health Check API")
public interface HealthCheckApi {


  @Operation(summary = "health check", description = "서버의 상태를 확인합니다.")
  @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "정상 처리"),})
  ResponseEntity<String> healthCheck();


  @Operation(
      summary = "로그인 health check",
      description = "로그인 상태에서 서버의 상태를 확인합니다."
  )
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "정상 처리"),
      @ApiResponse(
          responseCode = "401",
          description = "로그인 필요",
          content = @Content(schema = @Schema(implementation = ErrorResponse.class))
      )
  })
  ResponseEntity<SelectLoginUserResponseDto> healthCheckWithAuth(
      @AuthenticationPrincipal LoginUserInfoDto loginUserInfoDto);
}
