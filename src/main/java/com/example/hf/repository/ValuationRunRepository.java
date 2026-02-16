package com.example.hf.repository;

import com.example.hf.entity.ValuationRun;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ValuationRunRepository extends JpaRepository<ValuationRun, UUID> {
}
