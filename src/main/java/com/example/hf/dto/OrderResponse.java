package com.example.hf.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record OrderResponse(
    UUID orderId,
    UUID tenantId,
    UUID classId,
    String orderType,
    String orderStatus,
    LocalDate tradeDate,
    LocalDate navDate,
    LocalDate settleDate,
    String amountCcy,
    BigDecimal amount,
    BigDecimal units,
    UUID sourceInvAcctId,
    UUID targetInvAcctId,
    String notes
) {}
