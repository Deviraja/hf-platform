package com.example.hf.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "trial_balance_import", schema = "hf")
@Getter
@Setter
public class TrialBalanceImport {
  @Id
  @Column(name = "tb_import_id", nullable = false)
  private UUID tbImportId;

  @Column(name = "tenant_id", nullable = false)
  private UUID tenantId;
  @Column(name = "node_id", nullable = false)
  private UUID nodeId;
  @Column(name = "source_system", nullable = false)
  private String sourceSystem;
  @Column(name = "as_of_date", nullable = false)
  private LocalDate asOfDate;
  @Column(name = "status", nullable = false)
  private String status;
  @Column(name = "file_ref")
  private String fileRef;
  @Column(name = "imported_at", nullable = false)
  private OffsetDateTime importedAt;

}
