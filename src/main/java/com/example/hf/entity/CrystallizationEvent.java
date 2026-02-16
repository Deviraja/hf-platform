package com.example.hf.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "crystallization_event", schema = "hf")
@Getter
@Setter
public class CrystallizationEvent {
  @Id
  @Column(name = "crystal_id", nullable = false)
  private UUID crystalId;

  @Column(name = "tenant_id", nullable = false)
  private UUID tenantId;
  @Column(name = "class_id", nullable = false)
  private UUID classId;
  @Column(name = "period_start", nullable = false)
  private LocalDate periodStart;
  @Column(name = "period_end", nullable = false)
  private LocalDate periodEnd;
  @Column(name = "status", nullable = false)
  private String status;
  @Column(name = "total_perf_fee", nullable = false)
  private BigDecimal totalPerfFee;
  @Column(name = "created_at", nullable = false)
  private OffsetDateTime createdAt;

}
