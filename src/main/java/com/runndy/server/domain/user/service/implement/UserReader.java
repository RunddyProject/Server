package com.runndy.server.domain.user.service.implement;

import com.runndy.server.domain.user.repository.UserRepository;
import com.runndy.server.domain.user.repository.dto.request.SelectLoginUserQuery;
import com.runndy.server.domain.user.service.dto.LoginUserInfoDto;
import com.runndy.server.error.CustomException;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserReader {

  private final UserRepository userRepository;

  public Optional<LoginUserInfoDto> getUser(LoginUserInfoDto loginUserInfoDto) {
    SelectLoginUserQuery loginUserRequestDto =
        SelectLoginUserQuery.from(loginUserInfoDto);

    return userRepository.findUserByEmailAndSocialType(loginUserRequestDto)
                         .map(LoginUserInfoDto::from);
  }

  public Optional<LoginUserInfoDto> getLoginUserBySocialId(LoginUserInfoDto loginUserInfoDto) {
    SelectLoginUserQuery loginUserRequestDto =
        SelectLoginUserQuery.from(loginUserInfoDto);

    return userRepository.getUserBySocialIdAndSocialType(loginUserRequestDto)
                         .map(LoginUserInfoDto::from);
  }
}
