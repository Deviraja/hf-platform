package com.example.hf.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "dealing_order", schema = "hf")
@Getter
@Setter
public class DealingOrder {
  @Id
  @Column(name = "order_id", nullable = false)
  private UUID orderId;

  @Column(name = "tenant_id", nullable = false)
  private UUID tenantId;
  @Column(name = "class_id", nullable = false)
  private UUID classId;
  @Column(name = "order_type", nullable = false)
  private String orderType;
  @Column(name = "order_status", nullable = false)
  private String orderStatus;
  @Column(name = "trade_date", nullable = false)
  private LocalDate tradeDate;
  @Column(name = "nav_date")
  private LocalDate navDate;
  @Column(name = "settle_date")
  private LocalDate settleDate;
  @Column(name = "amount_ccy", nullable = false)
  private String amountCcy;
  @Column(name = "amount")
  private BigDecimal amount;
  @Column(name = "units")
  private BigDecimal units;
  @Column(name = "source_inv_acct_id")
  private UUID sourceInvAcctId;
  @Column(name = "target_inv_acct_id")
  private UUID targetInvAcctId;
  @Column(name = "created_by")
  private UUID createdBy;
  @Column(name = "notes")
  private String notes;
  @Column(name = "created_at", nullable = false)
  private OffsetDateTime createdAt;

}
