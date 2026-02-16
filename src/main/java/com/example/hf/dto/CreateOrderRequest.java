package com.example.hf.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public record CreateOrderRequest(
    @NotNull UUID tenantId,
    @NotNull UUID classId,
    @NotBlank String orderType,     // SUBSCRIBE|REDEEM|TRANSFER|SWITCH|SWAP
    LocalDate tradeDate,
    String amountCcy,
    BigDecimal amount,
    BigDecimal units,
    UUID sourceInvAcctId,
    UUID targetInvAcctId,
    @Valid List<CreateOrderLegRequest> legs,
    String notes
) {}
