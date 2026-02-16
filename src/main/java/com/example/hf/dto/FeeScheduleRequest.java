package com.example.hf.dto;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record FeeScheduleRequest(
    @NotNull UUID tenantId,
    @NotNull UUID classId,
    @NotNull LocalDate effectiveFrom,
    LocalDate effectiveTo,
    BigDecimal mgmtRateAnnual,
    String mgmtBase,            // NAV|GAV
    String accrualFreq,         // DAILY|MONTHLY
    BigDecimal perfRate,
    BigDecimal hurdleRate,
    Boolean hwmEnabled,
    String crystallizationFreq, // MONTHLY|QUARTERLY|ANNUAL
    String equalizationMethod   // SERIES|EQ_CREDITS
) {}
