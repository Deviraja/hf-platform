package com.example.hf.repository;

import com.example.hf.entity.FeeSchedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface FeeScheduleRepository extends JpaRepository<FeeSchedule, UUID> {
}
