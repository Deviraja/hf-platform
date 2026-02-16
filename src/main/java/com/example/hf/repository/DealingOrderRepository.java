package com.example.hf.repository;

import com.example.hf.entity.DealingOrder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface DealingOrderRepository extends JpaRepository<DealingOrder, UUID> {
}
