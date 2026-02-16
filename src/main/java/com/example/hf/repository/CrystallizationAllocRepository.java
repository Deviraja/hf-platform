package com.example.hf.repository;

import com.example.hf.entity.CrystallizationAlloc;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CrystallizationAllocRepository extends JpaRepository<CrystallizationAlloc, UUID> {
}
