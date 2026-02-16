package com.example.hf.repository;

import com.example.hf.entity.InvestorAccount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface InvestorAccountRepository extends JpaRepository<InvestorAccount, UUID> {
}
