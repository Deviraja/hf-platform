package com.example.hf.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record CreateInvestorRequest(
    @NotNull UUID tenantId,
    @NotBlank String investorType, // INDIVIDUAL|INSTITUTION
    @NotBlank String name,
    String countryCode
) {}
