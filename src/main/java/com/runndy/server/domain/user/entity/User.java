package com.runndy.server.domain.user.entity;

import com.runndy.server.domain.cmn.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
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
  @Enumerated(value = EnumType.STRING)
  private SocialType sclTyp; // 소셜 로그인 타입
}

