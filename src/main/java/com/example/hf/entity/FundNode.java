package com.example.hf.entity;

import jakarta.persistence.*;
import java.time.OffsetDateTime;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "fund_node", schema = "hf")
@Getter
@Setter
public class FundNode {
  @Id
  @Column(name = "node_id", nullable = false)
  private UUID nodeId;

  @Column(name = "tenant_id", nullable = false)
  private UUID tenantId;
  @Column(name = "fund_id", nullable = false)
  private UUID fundId;
  @Column(name = "node_type", nullable = false)
  private String nodeType;
  @Column(name = "node_name", nullable = false)
  private String nodeName;
  @Column(name = "node_ccy", nullable = false)
  private String nodeCcy;
  @Column(name = "status", nullable = false)
  private String status;
  @Column(name = "created_at", nullable = false)
  private OffsetDateTime createdAt;

}
