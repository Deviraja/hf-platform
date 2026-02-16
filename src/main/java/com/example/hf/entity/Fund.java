package com.example.hf.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "fund", schema = "hf")
@Getter
@Setter
public class Fund {
  @Id
  @Column(name = "fund_id", nullable = false)
  private UUID fundId;

  @Column(name = "tenant_id", nullable = false)
  private UUID tenantId;
  @Column(name = "fund_code", nullable = false)
  private String fundCode;
  @Column(name = "fund_name", nullable = false)
  private String fundName;
  @Column(name = "base_ccy", nullable = false)
  private String baseCcy;
  @Column(name = "inception_date")
  private LocalDate inceptionDate;
  @Column(name = "status", nullable = false)
  private String status;
  @Column(name = "created_at", nullable = false)
  private OffsetDateTime createdAt;

}
