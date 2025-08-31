package com.runndy.server.domain.user.service;

import com.runndy.server.domain.user.service.dto.request.SelectLoginUserRequestDto;
import com.runndy.server.domain.user.service.dto.response.SelectLoginUserResponseDto;
import com.runndy.server.domain.user.service.implement.UserManager;
import com.runndy.server.domain.user.service.implement.UserReader;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

  private final UserReader userReader;
  private final UserManager userManager;

  @Transactional
  public SelectLoginUserResponseDto getOrCreateUser(SelectLoginUserRequestDto loginUserRequestDto) {
    return userReader.getUser(loginUserRequestDto) // 존재하는 유저 조회
                     .orElseGet(() -> userManager.createUser(loginUserRequestDto)); // 없으면 유저 생성
  }
}
