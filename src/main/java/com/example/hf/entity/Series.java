package com.example.hf.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "series", schema = "hf")
@Getter
@Setter
public class Series {
  @Id
  @Column(name = "series_id", nullable = false)
  private UUID seriesId;

  @Column(name = "tenant_id", nullable = false)
  private UUID tenantId;
  @Column(name = "class_id", nullable = false)
  private UUID classId;
  @Column(name = "series_code", nullable = false)
  private String seriesCode;
  @Column(name = "start_date", nullable = false)
  private LocalDate startDate;
  @Column(name = "end_date")
  private LocalDate endDate;
  @Column(name = "series_nav_per_unit_at_open", nullable = false)
  private BigDecimal seriesNavPerUnitAtOpen;
  @Column(name = "created_at", nullable = false)
  private OffsetDateTime createdAt;

}
