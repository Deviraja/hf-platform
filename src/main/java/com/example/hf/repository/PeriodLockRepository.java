package com.example.hf.repository;

import com.example.hf.entity.PeriodLock;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PeriodLockRepository extends JpaRepository<PeriodLock, UUID> {
}
