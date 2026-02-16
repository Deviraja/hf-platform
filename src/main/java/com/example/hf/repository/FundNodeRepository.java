package com.example.hf.repository;

import com.example.hf.entity.FundNode;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface FundNodeRepository extends JpaRepository<FundNode, UUID> {
}
