package com.example.hf.repository;

import com.example.hf.entity.TrialBalanceImport;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TrialBalanceImportRepository extends JpaRepository<TrialBalanceImport, UUID> {
}
