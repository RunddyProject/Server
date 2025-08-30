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
  protected Boolean activated = true;

  @CreationTimestamp
  @Column(updatable = false)
  protected LocalDateTime createdAt;

  // UserID
  @Column(updatable = false)
  protected Long createdBy;

  @UpdateTimestamp
  @Column(insertable = false)
  protected LocalDateTime updatedAt;

  // UserID
  @Column(insertable = false)
  protected Long updatedBy;

  public void inactivate() {
    this.activated = false;
  }
}