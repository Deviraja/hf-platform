package com.example.hf.controller;

import com.example.hf.entity.AppUser;
import com.example.hf.repository.AppUserRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/users")
public class AppUserController {
  private final AppUserRepository repo;

  public AppUserController(AppUserRepository repo) {
    this.repo = repo;
  }

  @GetMapping
  public List<AppUser> list() {
    return repo.findAll();
  }

  @GetMapping("/{id}")
  public AppUser get(@PathVariable UUID id) {
    return repo.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "AppUser not found"));
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public AppUser create(@Valid @RequestBody AppUser body) {
    return repo.save(body);
  }

  @PutMapping("/{id}")
  public AppUser update(@PathVariable UUID id, @Valid @RequestBody AppUser body) {
    body.setUserId(id);
    return repo.save(body);
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void delete(@PathVariable UUID id) {
    repo.deleteById(id);
  }
}
