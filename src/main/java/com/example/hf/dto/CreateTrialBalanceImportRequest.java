package com.example.hf.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public record CreateTrialBalanceImportRequest(
    @NotNull UUID tenantId,
    @NotNull UUID nodeId,
    @NotBlank String sourceSystem,
    @NotNull LocalDate asOfDate,
    String fileRef,
    @Valid List<TrialBalanceLineDTO> lines
) {}
