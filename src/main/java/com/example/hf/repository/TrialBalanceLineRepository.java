package com.example.hf.repository;

import com.example.hf.entity.TrialBalanceLine;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TrialBalanceLineRepository extends JpaRepository<TrialBalanceLine, UUID> {
}
