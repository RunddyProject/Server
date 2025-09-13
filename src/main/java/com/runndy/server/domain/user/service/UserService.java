package com.runndy.server.domain.user.service;

import com.runndy.server.domain.user.service.dto.LoginUserInfoDto;
import com.runndy.server.domain.user.service.implement.UserManager;
import com.runndy.server.domain.user.service.implement.UserReader;
import com.runndy.server.error.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

  @Transactional(readOnly = true)
  public LoginUserInfoDto getLoginUser(LoginUserInfoDto loginUserInfoDto) {
    return userReader.getLoginUserBySocialId(loginUserInfoDto) // 존재하는 유저 조회
                     .orElseThrow(() -> new CustomException(
                         HttpStatus.UNAUTHORIZED,
                         "존재하지 않는 유저입니다.")); // 없으면 에러
  }
}
