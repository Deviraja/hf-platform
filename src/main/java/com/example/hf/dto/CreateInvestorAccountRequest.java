package com.example.hf.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.UUID;

public record CreateInvestorAccountRequest(
    @NotNull UUID tenantId,
    @NotNull UUID investorId,
    @NotNull UUID classId,
    @NotBlank String accountCode,
    LocalDate openDate
) {}
