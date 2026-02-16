package com.example.hf.repository;

import com.example.hf.entity.ClassNav;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ClassNavRepository extends JpaRepository<ClassNav, UUID> {
}
