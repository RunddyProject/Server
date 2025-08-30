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

  @Column(nullable = false, unique = true)
  private String usrNm; // 유저명

  @Column(nullable = false)
  private String sclTyp; // 소셜 로그인 타입 (SocialType)
}

