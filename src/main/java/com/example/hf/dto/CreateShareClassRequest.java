package com.example.hf.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalTime;
import java.util.UUID;

public record CreateShareClassRequest(
    @NotNull UUID tenantId,
    @NotNull UUID nodeId,
    @NotBlank String classCode,
    @NotBlank String className,
    @NotBlank String classCcy,
    LocalTime dealingCutoffTime,
    Integer settlementDays,
    Boolean isAccumulating
) {}
