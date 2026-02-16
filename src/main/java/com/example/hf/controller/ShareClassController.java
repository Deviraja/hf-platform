package com.example.hf.controller;

import com.example.hf.entity.ShareClass;
import com.example.hf.repository.ShareClassRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/share-classes")
public class ShareClassController {
  private final ShareClassRepository repo;

  public ShareClassController(ShareClassRepository repo) {
    this.repo = repo;
  }

  @GetMapping
  public List<ShareClass> list() {
    return repo.findAll();
  }

  @GetMapping("/{id}")
  public ShareClass get(@PathVariable UUID id) {
    return repo.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "ShareClass not found"));
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public ShareClass create(@Valid @RequestBody ShareClass body) {
    return repo.save(body);
  }

  @PutMapping("/{id}")
  public ShareClass update(@PathVariable UUID id, @Valid @RequestBody ShareClass body) {
    body.setClassId(id);
    return repo.save(body);
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void delete(@PathVariable UUID id) {
    repo.deleteById(id);
  }
}
