package com.runndy.server.domain.cmn.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import java.time.LocalDateTime;
import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class BaseEntity {

  @Id
  @Column
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  protected Long id;

  @Column
  protected Boolean activated = true; // 활성화여부

  @CreationTimestamp
  @Column(updatable = false)
  protected LocalDateTime createdAt; // 생성일시

  @Column(updatable = false)
  protected Long createdBy; // 생성자

  @UpdateTimestamp
  @Column(insertable = false)
  protected LocalDateTime updatedAt; // 수정일시

  @Column(insertable = false)
  protected Long updatedBy; // 수정자

  public void inactivate() {
    this.activated = false;
  }
}