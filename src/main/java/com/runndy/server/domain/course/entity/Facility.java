package com.runndy.server.domain.course.entity;

import com.runndy.server.domain.cmn.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(
    name = "facility",
    uniqueConstraints = {
        @UniqueConstraint(
            name = "uq_facility",
            columnNames = {"crs_id", "fclt_typ"}
        )
    }
)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Facility extends BaseEntity {

  @Column(name = "crs_id", nullable = false)
  protected Long courseId; // 코스 id

  @Column(name = "fclt_typ", nullable = false)
  protected String facilityType; // 코스 타입 (FacilityType)

  @Column(name = "fcltYn", nullable = false)
  protected Boolean facilityYn; // 시설유무

  @Column(name = "fcltAddr")
  protected String facilityAddress; // 시설 주소
}
