package com.example.hf.repository;

import com.example.hf.entity.EqualizationBalance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface EqualizationBalanceRepository extends JpaRepository<EqualizationBalance, UUID> {
}
