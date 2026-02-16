package com.example.hf.dto;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record CreateFundEdgeRequest(
    @NotNull UUID tenantId,
    @NotNull UUID parentNodeId,
    @NotNull UUID childNodeId,
    BigDecimal economicPct,
    LocalDate effectiveFrom,
    LocalDate effectiveTo
) {}
