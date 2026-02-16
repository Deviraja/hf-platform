package com.example.hf.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "fund_edge", schema = "hf")
@Getter
@Setter
public class FundEdge {
  @Id
  @Column(name = "edge_id", nullable = false)
  private UUID edgeId;

  @Column(name = "tenant_id", nullable = false)
  private UUID tenantId;
  @Column(name = "parent_node_id", nullable = false)
  private UUID parentNodeId;
  @Column(name = "child_node_id", nullable = false)
  private UUID childNodeId;
  @Column(name = "economic_pct")
  private BigDecimal economicPct;
  @Column(name = "effective_from", nullable = false)
  private LocalDate effectiveFrom;
  @Column(name = "effective_to")
  private LocalDate effectiveTo;
  @Column(name = "created_at", nullable = false)
  private OffsetDateTime createdAt;

}
