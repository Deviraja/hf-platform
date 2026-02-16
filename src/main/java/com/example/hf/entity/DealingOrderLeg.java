package com.example.hf.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "dealing_order_leg", schema = "hf")
@Getter
@Setter
public class DealingOrderLeg {
  @Id
  @Column(name = "leg_id", nullable = false)
  private UUID legId;

  @Column(name = "tenant_id", nullable = false)
  private UUID tenantId;
  @Column(name = "order_id", nullable = false)
  private UUID orderId;
  @Column(name = "leg_dir", nullable = false)
  private String legDir;
  @Column(name = "from_class_id")
  private UUID fromClassId;
  @Column(name = "to_class_id")
  private UUID toClassId;
  @Column(name = "from_inv_acct_id")
  private UUID fromInvAcctId;
  @Column(name = "to_inv_acct_id")
  private UUID toInvAcctId;
  @Column(name = "req_amount")
  private BigDecimal reqAmount;
  @Column(name = "req_units")
  private BigDecimal reqUnits;
  @Column(name = "currency", nullable = false)
  private String currency;
  @Column(name = "priced_amount")
  private BigDecimal pricedAmount;
  @Column(name = "priced_units")
  private BigDecimal pricedUnits;
  @Column(name = "price_nav_per_unit")
  private BigDecimal priceNavPerUnit;
  @Column(name = "created_at", nullable = false)
  private OffsetDateTime createdAt;

}
