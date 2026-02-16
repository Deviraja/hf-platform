package com.example.hf.repository;

import com.example.hf.entity.SeriesRollEvent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SeriesRollEventRepository extends JpaRepository<SeriesRollEvent, UUID> {
}
