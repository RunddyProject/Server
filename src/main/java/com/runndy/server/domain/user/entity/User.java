package com.runndy.server.domain.user.entity;

import com.runndy.server.domain.cmn.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseEntity {

  @Column(nullable = false, unique = true)
  private String email; // email

  @Column(name = "usr_nm", nullable = false, unique = true)
  private String userName; // 유저명

  @Column(name = "scl_typ", nullable = false)
  private String socialType; // 소셜 로그인 타입 (SocialType)

  @Column(name = "scl_id", nullable = false)
  private String socialId; // 소셜 제공자 ID

  @Column(name = "usr_typ", nullable = false)
  private String userType; // 유저 타입 (UserType)
}

