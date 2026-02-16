package com.example.hf.dto;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.UUID;

public record LockPeriodRequest(
    @NotNull UUID tenantId,
    @NotNull UUID nodeId,
    @NotNull LocalDate periodStart,
    @NotNull LocalDate periodEnd,
    UUID lockedBy
) {}
