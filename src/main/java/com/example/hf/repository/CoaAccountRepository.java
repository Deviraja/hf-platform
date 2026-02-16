package com.example.hf.repository;

import com.example.hf.entity.CoaAccount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CoaAccountRepository extends JpaRepository<CoaAccount, UUID> {
}
