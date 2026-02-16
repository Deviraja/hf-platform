package com.example.hf.dto;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.UUID;

public record RunValuationRequest(
    @NotNull UUID tenantId,
    @NotNull UUID nodeId,
    @NotNull LocalDate navDate,
    UUID tbImportId
) {}
