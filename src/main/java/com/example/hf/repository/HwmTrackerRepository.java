package com.example.hf.repository;

import com.example.hf.entity.HwmTracker;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface HwmTrackerRepository extends JpaRepository<HwmTracker, UUID> {
}
