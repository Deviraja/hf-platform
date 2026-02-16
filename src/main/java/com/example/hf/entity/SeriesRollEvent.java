package com.example.hf.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "series_roll_event", schema = "hf")
@Getter
@Setter
public class SeriesRollEvent {
  @Id
  @Column(name = "roll_id", nullable = false)
  private UUID rollId;

  @Column(name = "tenant_id", nullable = false)
  private UUID tenantId;
  @Column(name = "class_id", nullable = false)
  private UUID classId;
  @Column(name = "val_run_id", nullable = false)
  private UUID valRunId;
  @Column(name = "nav_date", nullable = false)
  private LocalDate navDate;
  @Column(name = "from_series_id")
  private UUID fromSeriesId;
  @Column(name = "to_series_id")
  private UUID toSeriesId;
  @Column(name = "roll_reason")
  private String rollReason;
  @Column(name = "created_at", nullable = false)
  private OffsetDateTime createdAt;

}
