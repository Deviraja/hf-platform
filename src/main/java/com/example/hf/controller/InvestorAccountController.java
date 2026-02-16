package com.example.hf.controller;

import com.example.hf.entity.InvestorAccount;
import com.example.hf.repository.InvestorAccountRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/investor-accounts")
public class InvestorAccountController {
  private final InvestorAccountRepository repo;

  public InvestorAccountController(InvestorAccountRepository repo) {
    this.repo = repo;
  }

  @GetMapping
  public List<InvestorAccount> list() {
    return repo.findAll();
  }

  @GetMapping("/{id}")
  public InvestorAccount get(@PathVariable UUID id) {
    return repo.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "InvestorAccount not found"));
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public InvestorAccount create(@Valid @RequestBody InvestorAccount body) {
    return repo.save(body);
  }

  @PutMapping("/{id}")
  public InvestorAccount update(@PathVariable UUID id, @Valid @RequestBody InvestorAccount body) {
    body.setInvAcctId(id);
    return repo.save(body);
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void delete(@PathVariable UUID id) {
    repo.deleteById(id);
  }
}
