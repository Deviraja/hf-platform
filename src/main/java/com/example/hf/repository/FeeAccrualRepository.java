package com.example.hf.repository;

import com.example.hf.entity.FeeAccrual;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface FeeAccrualRepository extends JpaRepository<FeeAccrual, UUID> {
}
