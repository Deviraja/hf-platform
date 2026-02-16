package com.example.hf.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "class_nav", schema = "hf")
@Getter
@Setter
public class ClassNav {
  @Id
  @Column(name = "class_nav_id", nullable = false)
  private UUID classNavId;

  @Column(name = "tenant_id", nullable = false)
  private UUID tenantId;
  @Column(name = "val_run_id", nullable = false)
  private UUID valRunId;
  @Column(name = "class_id", nullable = false)
  private UUID classId;
  @Column(name = "nav_total", nullable = false)
  private BigDecimal navTotal;
  @Column(name = "nav_per_unit", nullable = false)
  private BigDecimal navPerUnit;
  @Column(name = "units_outstanding", nullable = false)
  private BigDecimal unitsOutstanding;
  @Column(name = "subs_amount", nullable = false)
  private BigDecimal subsAmount;
  @Column(name = "reds_amount", nullable = false)
  private BigDecimal redsAmount;
  @Column(name = "created_at", nullable = false)
  private OffsetDateTime createdAt;

}
