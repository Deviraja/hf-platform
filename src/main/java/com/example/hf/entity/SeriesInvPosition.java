package com.example.hf.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "series_inv_position", schema = "hf")
@Getter
@Setter
public class SeriesInvPosition {
  @Id
  @Column(name = "sip_id", nullable = false)
  private UUID sipId;

  @Column(name = "tenant_id", nullable = false)
  private UUID tenantId;
  @Column(name = "series_id", nullable = false)
  private UUID seriesId;
  @Column(name = "inv_acct_id", nullable = false)
  private UUID invAcctId;
  @Column(name = "units", nullable = false)
  private BigDecimal units;
  @Column(name = "hwm_nav_per_unit")
  private BigDecimal hwmNavPerUnit;
  @Column(name = "created_at", nullable = false)
  private OffsetDateTime createdAt;

}
