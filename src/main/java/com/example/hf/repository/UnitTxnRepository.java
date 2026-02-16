package com.example.hf.repository;

import com.example.hf.entity.UnitTxn;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UnitTxnRepository extends JpaRepository<UnitTxn, UUID> {
}
