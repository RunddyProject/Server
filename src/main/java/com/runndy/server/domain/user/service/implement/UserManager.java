package com.runndy.server.domain.user.service.implement;

import com.runndy.server.domain.user.repository.UserRepository;
import com.runndy.server.domain.user.repository.dto.request.CreateUserCommand;
import com.runndy.server.domain.user.service.dto.LoginUserInfoDto;
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
   * @param loginUserInfoDto 로그인 요청 DTO
   * @return SelectLoginUserResponseDto 생성된 사용자 정보
   */
  public LoginUserInfoDto createUser(LoginUserInfoDto loginUserInfoDto) {
    CreateUserCommand createUserCommand = CreateUserCommand.from(loginUserInfoDto);

    int inserted = userRepository.createUser(createUserCommand);

    if (inserted != 1) {
      throw new RuntimeException("User creation failed");
    }

    return userReader.getUser(loginUserInfoDto)
                     .orElseThrow(
                         () -> new RuntimeException("User retrieval or creation failed"));
  }
}
