package com.example.hf.repository;

import com.example.hf.entity.Investor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface InvestorRepository extends JpaRepository<Investor, UUID> {
}
