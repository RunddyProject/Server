package com.runndy.server.domain.user.service.implement;

import com.runndy.server.domain.user.repository.UserRepository;
import com.runndy.server.domain.user.service.dto.request.SelectLoginUserRequestDto;
import com.runndy.server.domain.user.service.dto.response.SelectLoginUserResponseDto;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserReader {

  private final UserRepository userRepository;

  public Optional<SelectLoginUserResponseDto> getUser(SelectLoginUserRequestDto requestDto) {
    return userRepository.findUserByEmailAndSclTyp(
        requestDto.getEmail(),
        requestDto.getSocialType(),
        requestDto.getSocialId()
    );
  }
}
