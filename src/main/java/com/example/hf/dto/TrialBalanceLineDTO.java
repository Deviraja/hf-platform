package com.example.hf.dto;

import jakarta.validation.constraints.NotBlank;

import java.math.BigDecimal;

public record TrialBalanceLineDTO(
    @NotBlank String srcAccountCode,
    String srcAccountName,
    BigDecimal debit,
    BigDecimal credit,
    String currency,
    BigDecimal fxRate,
    BigDecimal baseAmount
) {}
