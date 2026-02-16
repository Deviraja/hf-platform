package com.example.hf.service;

import com.example.hf.dto.CreateInvestorAccountRequest;
import com.example.hf.dto.CreateInvestorRequest;
import com.example.hf.entity.Investor;
import com.example.hf.entity.InvestorAccount;
import com.example.hf.repository.InvestorAccountRepository;
import com.example.hf.repository.InvestorRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.time.LocalDate;
import java.util.UUID;

@Service
public class InvestorService {

  private final InvestorRepository investorRepository;
  private final InvestorAccountRepository investorAccountRepository;

  public InvestorService(InvestorRepository investorRepository, InvestorAccountRepository investorAccountRepository) {
    this.investorRepository = investorRepository;
    this.investorAccountRepository = investorAccountRepository;
  }

  @Transactional
  public Investor createInvestor(CreateInvestorRequest req) {
    var i = new Investor();
    i.setInvestorId(UUID.randomUUID());
    i.setTenantId(req.tenantId());
    i.setInvestorType(req.investorType());
    i.setName(req.name());
    i.setCountryCode(req.countryCode());
    i.setStatus("ACTIVE");
    i.setCreatedAt(OffsetDateTime.now());
    return investorRepository.save(i);
  }

  @Transactional
  public InvestorAccount openAccount(CreateInvestorAccountRequest req) {
    var a = new InvestorAccount();
    a.setInvAcctId(UUID.randomUUID());
    a.setTenantId(req.tenantId());
    a.setInvestorId(req.investorId());
    a.setClassId(req.classId());
    a.setAccountCode(req.accountCode());
    a.setOpenDate(req.openDate() == null ? LocalDate.now() : req.openDate());
    a.setStatus("ACTIVE");
    a.setCreatedAt(OffsetDateTime.now());
    return investorAccountRepository.save(a);
  }
}
