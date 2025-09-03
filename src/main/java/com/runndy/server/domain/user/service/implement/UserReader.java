package com.runndy.server.domain.user.service.implement;

import com.runndy.server.domain.user.repository.UserRepository;
import com.runndy.server.domain.user.repository.dto.request.SelectLoginUserRequestDto;
import com.runndy.server.domain.user.service.dto.LoginUserInfoDto;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserReader {

  private final UserRepository userRepository;

  public Optional<LoginUserInfoDto> getUser(LoginUserInfoDto loginUserInfoDto) {
    SelectLoginUserRequestDto loginUserRequestDto =
        SelectLoginUserRequestDto.from(loginUserInfoDto);

    return userRepository.findUserByEmailAndSocialType(loginUserRequestDto)
                         .map(LoginUserInfoDto::from);
  }
}
