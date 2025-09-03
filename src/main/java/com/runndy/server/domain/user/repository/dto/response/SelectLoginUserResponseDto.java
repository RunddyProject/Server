package com.runndy.server.domain.user.repository.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class SelectLoginUserResponseDto {

  private String email;
  private String userName;
  private String socialType;
  private String socialId;
  private String userType;
}
