package com.example.hf.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "valuation_run", schema = "hf")
@Getter
@Setter
public class ValuationRun {
  @Id
  @Column(name = "val_run_id", nullable = false)
  private UUID valRunId;

  @Column(name = "tenant_id", nullable = false)
  private UUID tenantId;
  @Column(name = "node_id", nullable = false)
  private UUID nodeId;
  @Column(name = "nav_date", nullable = false)
  private LocalDate navDate;
  @Column(name = "run_status", nullable = false)
  private String runStatus;
  @Column(name = "tb_import_id")
  private UUID tbImportId;
  @Column(name = "created_at", nullable = false)
  private OffsetDateTime createdAt;
  @Column(name = "completed_at")
  private OffsetDateTime completedAt;

}
