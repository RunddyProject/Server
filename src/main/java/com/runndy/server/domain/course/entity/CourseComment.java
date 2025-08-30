package com.runndy.server.domain.course.entity;


import com.runndy.server.domain.cmn.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CourseComment extends BaseEntity {

  @Column
  protected Long crsId; // 코스 id

  @Column
  protected Long usrId; // 유저 id

  @Column
  protected Long cmtSeq; // 댓글순서

  @Column
  protected String cnt; // 내용
}
