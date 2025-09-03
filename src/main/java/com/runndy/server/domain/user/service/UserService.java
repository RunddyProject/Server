package com.runndy.server.domain.user.service;

import com.runndy.server.domain.user.service.dto.LoginUserInfoDto;
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
  public LoginUserInfoDto getOrCreateUser(LoginUserInfoDto loginUserInfoDto) {
    return userReader.getUser(loginUserInfoDto) // 존재하는 유저 조회
                     .orElseGet(() -> userManager.createUser(loginUserInfoDto)); // 없으면 유저 생성
  }
}
