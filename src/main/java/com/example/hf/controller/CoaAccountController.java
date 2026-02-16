package com.example.hf.controller;

import com.example.hf.entity.CoaAccount;
import com.example.hf.repository.CoaAccountRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/coa-accounts")
public class CoaAccountController {
  private final CoaAccountRepository repo;

  public CoaAccountController(CoaAccountRepository repo) {
    this.repo = repo;
  }

  @GetMapping
  public List<CoaAccount> list() {
    return repo.findAll();
  }

  @GetMapping("/{id}")
  public CoaAccount get(@PathVariable UUID id) {
    return repo.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "CoaAccount not found"));
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public CoaAccount create(@Valid @RequestBody CoaAccount body) {
    return repo.save(body);
  }

  @PutMapping("/{id}")
  public CoaAccount update(@PathVariable UUID id, @Valid @RequestBody CoaAccount body) {
    body.setCoaId(id);
    return repo.save(body);
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void delete(@PathVariable UUID id) {
    repo.deleteById(id);
  }
}
