package com.example.hf.repository;

import com.example.hf.entity.SeriesInvPosition;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SeriesInvPositionRepository extends JpaRepository<SeriesInvPosition, UUID> {
}
