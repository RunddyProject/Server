package com.runndy.server.domain.user.service.implement;

import com.runndy.server.domain.user.repository.UserRepository;
import com.runndy.server.domain.user.service.dto.request.CreateUserRequestDto;
import com.runndy.server.domain.user.service.dto.request.SelectLoginUserRequestDto;
import com.runndy.server.domain.user.service.dto.response.SelectLoginUserResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserManager {

  private final UserReader userReader;
  private final UserRepository userRepository;

  /**
   * 회원가입
   *
   * @param loginUserRequestDto 로그인 요청 DTO
   * @return SelectLoginUserResponseDto 생성된 사용자 정보
   */
  public SelectLoginUserResponseDto createUser(
      SelectLoginUserRequestDto loginUserRequestDto) {
    CreateUserRequestDto createUserRequestDto = CreateUserRequestDto.from(loginUserRequestDto);

    int inserted = userRepository.createUser(createUserRequestDto);

    if (inserted != 1) {
      throw new RuntimeException("User creation failed");
    }

    return userReader.getUser(loginUserRequestDto)
                     .orElseThrow(
                         () -> new RuntimeException("User retrieval or creation failed"));
  }
}
