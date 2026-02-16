package com.example.hf.entity;

import jakarta.persistence.*;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "share_class", schema = "hf")
@Getter
@Setter
public class ShareClass {
  @Id
  @Column(name = "class_id", nullable = false)
  private UUID classId;

  @Column(name = "tenant_id", nullable = false)
  private UUID tenantId;
  @Column(name = "node_id", nullable = false)
  private UUID nodeId;
  @Column(name = "class_code", nullable = false)
  private String classCode;
  @Column(name = "class_name", nullable = false)
  private String className;
  @Column(name = "class_ccy", nullable = false)
  private String classCcy;
  @Column(name = "dealing_cutoff_time")
  private LocalTime dealingCutoffTime;
  @Column(name = "settlement_days", nullable = false)
  private Integer settlementDays;
  @Column(name = "is_accumulating", nullable = false)
  private Boolean isAccumulating;
  @Column(name = "status", nullable = false)
  private String status;
  @Column(name = "created_at", nullable = false)
  private OffsetDateTime createdAt;

}
