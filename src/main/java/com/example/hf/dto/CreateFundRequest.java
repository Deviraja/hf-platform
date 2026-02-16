package com.example.hf.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.UUID;

public record CreateFundRequest(
    @NotNull UUID tenantId,
    @NotBlank String fundCode,
    @NotBlank String fundName,
    @NotBlank String baseCcy,
    LocalDate inceptionDate
) {}
