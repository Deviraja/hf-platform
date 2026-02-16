package com.example.hf.repository;

import com.example.hf.entity.DealingOrderLeg;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface DealingOrderLegRepository extends JpaRepository<DealingOrderLeg, UUID> {
}
