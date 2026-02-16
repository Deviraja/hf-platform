package com.example.hf.repository;

import com.example.hf.entity.GlEntryLine;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface GlEntryLineRepository extends JpaRepository<GlEntryLine, UUID> {
}
