package com.example.hf.repository;

import com.example.hf.entity.GlEntry;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface GlEntryRepository extends JpaRepository<GlEntry, UUID> {
}
