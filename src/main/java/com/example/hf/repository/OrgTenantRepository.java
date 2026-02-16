package com.example.hf.repository;

import com.example.hf.entity.OrgTenant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface OrgTenantRepository extends JpaRepository<OrgTenant, UUID> {
}
