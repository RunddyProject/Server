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

  @Column(name = "crs_id", nullable = false)
  protected Long courseId; // 코스 id

  @Column(name = "usr_id", nullable = false)
  protected Long userId; // 유저 id

  @Column(name = "cmt_seq", nullable = false)
  protected Long commentSeq; // 댓글순서

  @Column(name = "content")
  protected String content; // 내용
}
