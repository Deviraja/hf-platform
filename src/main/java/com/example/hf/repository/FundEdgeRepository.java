package com.example.hf.repository;

import com.example.hf.entity.FundEdge;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface FundEdgeRepository extends JpaRepository<FundEdge, UUID> {
}
