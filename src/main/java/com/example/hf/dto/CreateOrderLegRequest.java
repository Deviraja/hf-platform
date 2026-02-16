package com.example.hf.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.UUID;

public record CreateOrderLegRequest(
    @NotBlank String legDir, // OUT|IN|FX_OUT|FX_IN
    UUID fromClassId,
    UUID toClassId,
    UUID fromInvAcctId,
    UUID toInvAcctId,
    BigDecimal reqAmount,
    BigDecimal reqUnits,
    @NotBlank String currency
) {}
