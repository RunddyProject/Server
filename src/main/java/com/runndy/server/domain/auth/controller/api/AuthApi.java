package com.runndy.server.domain.auth.controller.api;

import com.runndy.server.domain.auth.controller.dto.CreateAccessTokenResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.Optional;
import org.springframework.http.ResponseEntity;

@Tag(name = "인증", description = "인증 관련 API")
public interface AuthApi {

  @Operation(summary = "토큰 재발급", description = "Access token 재발급")
  @Parameter(name = "refreshToken", in= ParameterIn.COOKIE, hidden = true)
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "정상 처리"),
  })
  ResponseEntity<CreateAccessTokenResponseDto> refresh(String refresh);


  @Operation(summary = "로그아웃", description = "서버의 상태를 확인합니다.")
  @Parameter(name = "refreshToken", in= ParameterIn.COOKIE, hidden = true)
  @Parameter(name = "authHeader", in= ParameterIn.HEADER, hidden = true)
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "정상 처리"),
  })
  ResponseEntity<Void> logout(Optional<String> authHeader, String refresh);
}
