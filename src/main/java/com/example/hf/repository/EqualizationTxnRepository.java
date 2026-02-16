package com.example.hf.repository;

import com.example.hf.entity.EqualizationTxn;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface EqualizationTxnRepository extends JpaRepository<EqualizationTxn, UUID> {
}
