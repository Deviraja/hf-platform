package com.example.hf.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "period_lock", schema = "hf")
@Getter
@Setter
public class PeriodLock {
  @Id
  @Column(name = "lock_id", nullable = false)
  private UUID lockId;

  @Column(name = "tenant_id", nullable = false)
  private UUID tenantId;
  @Column(name = "node_id", nullable = false)
  private UUID nodeId;
  @Column(name = "period_start", nullable = false)
  private LocalDate periodStart;
  @Column(name = "period_end", nullable = false)
  private LocalDate periodEnd;
  @Column(name = "locked_by")
  private UUID lockedBy;
  @Column(name = "locked_at", nullable = false)
  private OffsetDateTime lockedAt;

}
