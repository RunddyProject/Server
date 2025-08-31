package com.runndy.server.domain.user.service.dto.request;

import com.runndy.server.domain.user.entity.UserType;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateUserRequestDto {

  private String email;
  private String userName;
  private String socialType;
  private String socialId;
  private String userType;

  public static CreateUserRequestDto from(SelectLoginUserRequestDto requestDto) {
    return CreateUserRequestDto.builder()
                               .email(requestDto.getEmail())
                               .userName(requestDto.getName())
                               .socialType(requestDto.getSocialType())
                               .socialId(requestDto.getSocialId())
                               .userType(UserType.USER.toString())
                               .build();
  }
}
