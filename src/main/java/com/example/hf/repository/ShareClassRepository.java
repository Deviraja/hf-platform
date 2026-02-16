package com.example.hf.repository;

import com.example.hf.entity.ShareClass;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ShareClassRepository extends JpaRepository<ShareClass, UUID> {
}
