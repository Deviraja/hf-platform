-- ============================================================
-- Hedge Fund Administration Platform (Unitized NAV per Share)
-- PostgreSQL SQL Dump (DDL Starter Pack)
-- Generated: 2026-02-16 15:33:40
-- Schema: hf
-- Notes:
--   * Uses gen_random_uuid() from pgcrypto.
--   * Run on an empty database (or safely re-run; uses IF NOT EXISTS).
-- ============================================================

CREATE EXTENSION IF NOT EXISTS pgcrypto;

CREATE SCHEMA IF NOT EXISTS hf;
SET search_path = hf, public;

-- ============================================================
-- 1) TENANCY & USERS (minimal)
-- ============================================================

CREATE TABLE IF NOT EXISTS org_tenant (
  tenant_id   uuid PRIMARY KEY DEFAULT gen_random_uuid(),
  name        text NOT NULL,
  status      text NOT NULL DEFAULT 'ACTIVE',
  created_at  timestamptz NOT NULL DEFAULT now(),
  CONSTRAINT uq_org_tenant_name UNIQUE (name)
);

CREATE TABLE IF NOT EXISTS app_user (
  user_id     uuid PRIMARY KEY DEFAULT gen_random_uuid(),
  tenant_id   uuid NOT NULL REFERENCES org_tenant(tenant_id) ON DELETE CASCADE,
  email       text NOT NULL,
  full_name   text NOT NULL,
  status      text NOT NULL DEFAULT 'ACTIVE',
  created_at  timestamptz NOT NULL DEFAULT now(),
  CONSTRAINT uq_app_user_email UNIQUE (tenant_id, email)
);

CREATE INDEX IF NOT EXISTS ix_app_user_tenant ON app_user(tenant_id);

-- ============================================================
-- 2) FUND / STRUCTURE / SHARE CLASSES
-- ============================================================

CREATE TABLE IF NOT EXISTS fund (
  fund_id        uuid PRIMARY KEY DEFAULT gen_random_uuid(),
  tenant_id      uuid NOT NULL REFERENCES org_tenant(tenant_id) ON DELETE CASCADE,
  fund_code      text NOT NULL,
  fund_name      text NOT NULL,
  base_ccy       char(3) NOT NULL,
  inception_date date,
  status         text NOT NULL DEFAULT 'ACTIVE',
  created_at     timestamptz NOT NULL DEFAULT now(),
  CONSTRAINT uq_fund_code UNIQUE (tenant_id, fund_code)
);

CREATE INDEX IF NOT EXISTS ix_fund_tenant ON fund(tenant_id);

CREATE TABLE IF NOT EXISTS fund_node (
  node_id     uuid PRIMARY KEY DEFAULT gen_random_uuid(),
  tenant_id   uuid NOT NULL REFERENCES org_tenant(tenant_id) ON DELETE CASCADE,
  fund_id     uuid NOT NULL REFERENCES fund(fund_id) ON DELETE CASCADE,
  node_type   text NOT NULL,
  node_name   text NOT NULL,
  node_ccy    char(3) NOT NULL,
  status      text NOT NULL DEFAULT 'ACTIVE',
  created_at  timestamptz NOT NULL DEFAULT now(),
  CONSTRAINT ck_fund_node_type CHECK (node_type IN ('MASTER','FEEDER','SUBFUND')),
  CONSTRAINT uq_fund_node_name UNIQUE (tenant_id, fund_id, node_name)
);

CREATE INDEX IF NOT EXISTS ix_fund_node_fund ON fund_node(fund_id);
CREATE INDEX IF NOT EXISTS ix_fund_node_tenant ON fund_node(tenant_id);

CREATE TABLE IF NOT EXISTS fund_edge (
  edge_id        uuid PRIMARY KEY DEFAULT gen_random_uuid(),
  tenant_id      uuid NOT NULL REFERENCES org_tenant(tenant_id) ON DELETE CASCADE,
  parent_node_id uuid NOT NULL REFERENCES fund_node(node_id) ON DELETE RESTRICT,
  child_node_id  uuid NOT NULL REFERENCES fund_node(node_id) ON DELETE RESTRICT,
  economic_pct   numeric(9,6),
  effective_from date NOT NULL DEFAULT CURRENT_DATE,
  effective_to   date,
  created_at     timestamptz NOT NULL DEFAULT now(),
  CONSTRAINT ck_edge_not_self CHECK (parent_node_id <> child_node_id),
  CONSTRAINT uq_fund_edge UNIQUE (tenant_id, parent_node_id, child_node_id, effective_from)
);

CREATE INDEX IF NOT EXISTS ix_fund_edge_parent ON fund_edge(parent_node_id);
CREATE INDEX IF NOT EXISTS ix_fund_edge_child ON fund_edge(child_node_id);

CREATE TABLE IF NOT EXISTS share_class (
  class_id             uuid PRIMARY KEY DEFAULT gen_random_uuid(),
  tenant_id            uuid NOT NULL REFERENCES org_tenant(tenant_id) ON DELETE CASCADE,
  node_id              uuid NOT NULL REFERENCES fund_node(node_id) ON DELETE CASCADE,
  class_code           text NOT NULL,
  class_name           text NOT NULL,
  class_ccy            char(3) NOT NULL,
  dealing_cutoff_time  time,
  settlement_days      int NOT NULL DEFAULT 2,
  is_accumulating      boolean NOT NULL DEFAULT true,
  status               text NOT NULL DEFAULT 'ACTIVE',
  created_at           timestamptz NOT NULL DEFAULT now(),
  CONSTRAINT uq_share_class_code UNIQUE (tenant_id, node_id, class_code)
);

CREATE INDEX IF NOT EXISTS ix_share_class_node ON share_class(node_id);
CREATE INDEX IF NOT EXISTS ix_share_class_tenant ON share_class(tenant_id);

-- ============================================================
-- 3) INVESTORS & ACCOUNTS
-- ============================================================

CREATE TABLE IF NOT EXISTS investor (
  investor_id   uuid PRIMARY KEY DEFAULT gen_random_uuid(),
  tenant_id     uuid NOT NULL REFERENCES org_tenant(tenant_id) ON DELETE CASCADE,
  investor_type text NOT NULL,
  name          text NOT NULL,
  country_code  char(2),
  status        text NOT NULL DEFAULT 'ACTIVE',
  created_at    timestamptz NOT NULL DEFAULT now(),
  CONSTRAINT ck_investor_type CHECK (investor_type IN ('INDIVIDUAL','INSTITUTION'))
);

CREATE INDEX IF NOT EXISTS ix_investor_tenant ON investor(tenant_id);

CREATE TABLE IF NOT EXISTS investor_account (
  inv_acct_id   uuid PRIMARY KEY DEFAULT gen_random_uuid(),
  tenant_id     uuid NOT NULL REFERENCES org_tenant(tenant_id) ON DELETE CASCADE,
  investor_id   uuid NOT NULL REFERENCES investor(investor_id) ON DELETE RESTRICT,
  class_id      uuid NOT NULL REFERENCES share_class(class_id) ON DELETE RESTRICT,
  account_code  text NOT NULL,
  open_date     date NOT NULL DEFAULT CURRENT_DATE,
  status        text NOT NULL DEFAULT 'ACTIVE',
  created_at    timestamptz NOT NULL DEFAULT now(),
  CONSTRAINT uq_inv_acct_code UNIQUE (tenant_id, account_code),
  CONSTRAINT uq_inv_acct_per_class UNIQUE (tenant_id, investor_id, class_id)
);

CREATE INDEX IF NOT EXISTS ix_inv_acct_investor ON investor_account(investor_id);
CREATE INDEX IF NOT EXISTS ix_inv_acct_class ON investor_account(class_id);
CREATE INDEX IF NOT EXISTS ix_inv_acct_tenant ON investor_account(tenant_id);

-- ============================================================
-- 4) DEALING / ORDERS
-- ============================================================

CREATE TABLE IF NOT EXISTS dealing_order (
  order_id           uuid PRIMARY KEY DEFAULT gen_random_uuid(),
  tenant_id          uuid NOT NULL REFERENCES org_tenant(tenant_id) ON DELETE CASCADE,
  class_id           uuid NOT NULL REFERENCES share_class(class_id) ON DELETE RESTRICT,
  order_type         text NOT NULL,
  order_status       text NOT NULL,
  trade_date         date NOT NULL DEFAULT CURRENT_DATE,
  nav_date           date,
  settle_date        date,
  amount_ccy         char(3) NOT NULL,
  amount             numeric(20,6),
  units              numeric(20,10),
  source_inv_acct_id uuid REFERENCES investor_account(inv_acct_id) ON DELETE RESTRICT,
  target_inv_acct_id uuid REFERENCES investor_account(inv_acct_id) ON DELETE RESTRICT,
  created_by         uuid REFERENCES app_user(user_id) ON DELETE SET NULL,
  notes              text,
  created_at         timestamptz NOT NULL DEFAULT now(),
  CONSTRAINT ck_order_type CHECK (order_type IN ('SUBSCRIBE','REDEEM','TRANSFER','SWITCH','SWAP')),
  CONSTRAINT ck_order_status CHECK (order_status IN ('DRAFT','SUBMITTED','ACCEPTED','PRICED','SETTLED','CANCELLED','REJECTED'))
);

CREATE INDEX IF NOT EXISTS ix_order_tenant_status ON dealing_order(tenant_id, order_status);
CREATE INDEX IF NOT EXISTS ix_order_class_navdate ON dealing_order(tenant_id, class_id, nav_date);
CREATE INDEX IF NOT EXISTS ix_order_trade_date ON dealing_order(tenant_id, trade_date);

CREATE TABLE IF NOT EXISTS dealing_order_leg (
  leg_id            uuid PRIMARY KEY DEFAULT gen_random_uuid(),
  tenant_id         uuid NOT NULL REFERENCES org_tenant(tenant_id) ON DELETE CASCADE,
  order_id          uuid NOT NULL REFERENCES dealing_order(order_id) ON DELETE CASCADE,
  leg_dir           text NOT NULL,
  from_class_id     uuid REFERENCES share_class(class_id) ON DELETE RESTRICT,
  to_class_id       uuid REFERENCES share_class(class_id) ON DELETE RESTRICT,
  from_inv_acct_id  uuid REFERENCES investor_account(inv_acct_id) ON DELETE RESTRICT,
  to_inv_acct_id    uuid REFERENCES investor_account(inv_acct_id) ON DELETE RESTRICT,
  req_amount        numeric(20,6),
  req_units         numeric(20,10),
  currency          char(3) NOT NULL,
  priced_amount     numeric(20,6),
  priced_units      numeric(20,10),
  price_nav_per_unit numeric(20,10),
  created_at        timestamptz NOT NULL DEFAULT now(),
  CONSTRAINT ck_leg_dir CHECK (leg_dir IN ('OUT','IN','FX_OUT','FX_IN'))
);

CREATE INDEX IF NOT EXISTS ix_leg_order ON dealing_order_leg(order_id);
CREATE INDEX IF NOT EXISTS ix_leg_tenant ON dealing_order_leg(tenant_id);

-- ============================================================
-- 5) COA + TRIAL BALANCE IMPORT
-- ============================================================

CREATE TABLE IF NOT EXISTS coa_account (
  coa_id         uuid PRIMARY KEY DEFAULT gen_random_uuid(),
  tenant_id      uuid NOT NULL REFERENCES org_tenant(tenant_id) ON DELETE CASCADE,
  account_code   text NOT NULL,
  name           text NOT NULL,
  account_type   text NOT NULL,
  normal_balance text NOT NULL,
  created_at     timestamptz NOT NULL DEFAULT now(),
  CONSTRAINT ck_coa_type CHECK (account_type IN ('ASSET','LIAB','INCOME','EXPENSE','EQUITY')),
  CONSTRAINT ck_coa_normal CHECK (normal_balance IN ('DEBIT','CREDIT')),
  CONSTRAINT uq_coa_code UNIQUE (tenant_id, account_code)
);

CREATE INDEX IF NOT EXISTS ix_coa_tenant ON coa_account(tenant_id);

CREATE TABLE IF NOT EXISTS trial_balance_import (
  tb_import_id   uuid PRIMARY KEY DEFAULT gen_random_uuid(),
  tenant_id      uuid NOT NULL REFERENCES org_tenant(tenant_id) ON DELETE CASCADE,
  node_id        uuid NOT NULL REFERENCES fund_node(node_id) ON DELETE RESTRICT,
  source_system  text NOT NULL,
  as_of_date     date NOT NULL,
  status         text NOT NULL DEFAULT 'IMPORTED',
  file_ref       text,
  imported_at    timestamptz NOT NULL DEFAULT now(),
  CONSTRAINT ck_tb_status CHECK (status IN ('IMPORTED','VALIDATED','REJECTED')),
  CONSTRAINT uq_tb_import UNIQUE (tenant_id, node_id, as_of_date, source_system)
);

CREATE INDEX IF NOT EXISTS ix_tb_import_node_date ON trial_balance_import(tenant_id, node_id, as_of_date);

CREATE TABLE IF NOT EXISTS trial_balance_line (
  tb_line_id       uuid PRIMARY KEY DEFAULT gen_random_uuid(),
  tenant_id        uuid NOT NULL REFERENCES org_tenant(tenant_id) ON DELETE CASCADE,
  tb_import_id     uuid NOT NULL REFERENCES trial_balance_import(tb_import_id) ON DELETE CASCADE,
  src_account_code text NOT NULL,
  src_account_name text,
  debit            numeric(24,6) NOT NULL DEFAULT 0,
  credit           numeric(24,6) NOT NULL DEFAULT 0,
  currency         char(3),
  fx_rate          numeric(18,10),
  base_amount      numeric(24,6),
  mapped_coa_id    uuid REFERENCES coa_account(coa_id) ON DELETE SET NULL,
  created_at       timestamptz NOT NULL DEFAULT now()
);

CREATE INDEX IF NOT EXISTS ix_tb_line_import_acct ON trial_balance_line(tenant_id, tb_import_id, src_account_code);
CREATE INDEX IF NOT EXISTS ix_tb_line_mapped_coa ON trial_balance_line(mapped_coa_id);

-- ============================================================
-- 6) VALUATION RUN + CLASS NAV
-- ============================================================

CREATE TABLE IF NOT EXISTS valuation_run (
  val_run_id    uuid PRIMARY KEY DEFAULT gen_random_uuid(),
  tenant_id     uuid NOT NULL REFERENCES org_tenant(tenant_id) ON DELETE CASCADE,
  node_id       uuid NOT NULL REFERENCES fund_node(node_id) ON DELETE RESTRICT,
  nav_date      date NOT NULL,
  run_status    text NOT NULL DEFAULT 'DRAFT',
  tb_import_id  uuid REFERENCES trial_balance_import(tb_import_id) ON DELETE SET NULL,
  created_at    timestamptz NOT NULL DEFAULT now(),
  completed_at  timestamptz,
  CONSTRAINT ck_val_run_status CHECK (run_status IN ('DRAFT','RUNNING','COMPLETE','FAILED','LOCKED')),
  CONSTRAINT uq_val_run UNIQUE (tenant_id, node_id, nav_date)
);

CREATE INDEX IF NOT EXISTS ix_valrun_tenant_node_date ON valuation_run(tenant_id, node_id, nav_date);

CREATE TABLE IF NOT EXISTS class_nav (
  class_nav_id       uuid PRIMARY KEY DEFAULT gen_random_uuid(),
  tenant_id          uuid NOT NULL REFERENCES org_tenant(tenant_id) ON DELETE CASCADE,
  val_run_id         uuid NOT NULL REFERENCES valuation_run(val_run_id) ON DELETE CASCADE,
  class_id           uuid NOT NULL REFERENCES share_class(class_id) ON DELETE RESTRICT,
  nav_total          numeric(24,6) NOT NULL,
  nav_per_unit       numeric(24,10) NOT NULL,
  units_outstanding  numeric(24,10) NOT NULL,
  subs_amount        numeric(24,6)  NOT NULL DEFAULT 0,
  reds_amount        numeric(24,6)  NOT NULL DEFAULT 0,
  created_at         timestamptz NOT NULL DEFAULT now(),
  CONSTRAINT uq_class_nav UNIQUE (tenant_id, val_run_id, class_id)
);

CREATE INDEX IF NOT EXISTS ix_class_nav_class ON class_nav(tenant_id, class_id);
CREATE INDEX IF NOT EXISTS ix_class_nav_valrun ON class_nav(val_run_id);

-- ============================================================
-- 7) UNIT LEDGER
-- ============================================================

CREATE TABLE IF NOT EXISTS unit_txn (
  unit_txn_id   uuid PRIMARY KEY DEFAULT gen_random_uuid(),
  tenant_id     uuid NOT NULL REFERENCES org_tenant(tenant_id) ON DELETE CASCADE,
  inv_acct_id   uuid NOT NULL REFERENCES investor_account(inv_acct_id) ON DELETE RESTRICT,
  class_id      uuid NOT NULL REFERENCES share_class(class_id) ON DELETE RESTRICT,
  nav_date      date NOT NULL,
  txn_type      text NOT NULL,
  units         numeric(24,10) NOT NULL,
  price         numeric(24,10),
  amount        numeric(24,6),
  order_id      uuid REFERENCES dealing_order(order_id) ON DELETE SET NULL,
  val_run_id    uuid REFERENCES valuation_run(val_run_id) ON DELETE SET NULL,
  created_at    timestamptz NOT NULL DEFAULT now(),
  CONSTRAINT ck_unit_txn_type CHECK (txn_type IN (
    'SUB_ISSUE','RED_CANCEL','TRANSFER_IN','TRANSFER_OUT','SWITCH_IN','SWITCH_OUT','ADJUST'
  ))
);

CREATE INDEX IF NOT EXISTS ix_unit_txn_acct_date ON unit_txn(tenant_id, inv_acct_id, nav_date);
CREATE INDEX IF NOT EXISTS ix_unit_txn_class_date ON unit_txn(tenant_id, class_id, nav_date);
CREATE INDEX IF NOT EXISTS ix_unit_txn_order ON unit_txn(order_id);

CREATE TABLE IF NOT EXISTS unit_balance_snapshot (
  tenant_id     uuid NOT NULL REFERENCES org_tenant(tenant_id) ON DELETE CASCADE,
  inv_acct_id   uuid NOT NULL REFERENCES investor_account(inv_acct_id) ON DELETE CASCADE,
  nav_date      date NOT NULL,
  units_balance numeric(24,10) NOT NULL,
  nav_per_unit  numeric(24,10) NOT NULL,
  market_value  numeric(24,6)  NOT NULL,
  created_at    timestamptz NOT NULL DEFAULT now(),
  PRIMARY KEY (tenant_id, inv_acct_id, nav_date)
);

CREATE INDEX IF NOT EXISTS ix_unit_bal_date ON unit_balance_snapshot(tenant_id, nav_date);

-- ============================================================
-- 8) FEES + HWM + crystallization
-- ============================================================

CREATE TABLE IF NOT EXISTS fee_schedule (
  fee_schedule_id       uuid PRIMARY KEY DEFAULT gen_random_uuid(),
  tenant_id             uuid NOT NULL REFERENCES org_tenant(tenant_id) ON DELETE CASCADE,
  class_id              uuid NOT NULL REFERENCES share_class(class_id) ON DELETE RESTRICT,
  effective_from        date NOT NULL,
  effective_to          date,
  mgmt_rate_annual      numeric(9,6) NOT NULL DEFAULT 0,
  mgmt_base             text NOT NULL DEFAULT 'NAV',
  accrual_freq          text NOT NULL DEFAULT 'DAILY',
  perf_rate             numeric(9,6) NOT NULL DEFAULT 0,
  hurdle_rate           numeric(9,6),
  hwm_enabled           boolean NOT NULL DEFAULT true,
  crystallization_freq  text NOT NULL DEFAULT 'ANNUAL',
  equalization_method   text NOT NULL DEFAULT 'SERIES',
  created_at            timestamptz NOT NULL DEFAULT now(),
  CONSTRAINT ck_mgmt_base CHECK (mgmt_base IN ('NAV','GAV')),
  CONSTRAINT ck_accrual_freq CHECK (accrual_freq IN ('DAILY','MONTHLY')),
  CONSTRAINT ck_crystal_freq CHECK (crystallization_freq IN ('MONTHLY','QUARTERLY','ANNUAL')),
  CONSTRAINT ck_equalization_method CHECK (equalization_method IN ('SERIES','EQ_CREDITS')),
  CONSTRAINT uq_fee_schedule UNIQUE (tenant_id, class_id, effective_from)
);

CREATE INDEX IF NOT EXISTS ix_fee_schedule_class ON fee_schedule(tenant_id, class_id, effective_from);

CREATE TABLE IF NOT EXISTS fee_accrual (
  fee_accrual_id   uuid PRIMARY KEY DEFAULT gen_random_uuid(),
  tenant_id        uuid NOT NULL REFERENCES org_tenant(tenant_id) ON DELETE CASCADE,
  val_run_id       uuid NOT NULL REFERENCES valuation_run(val_run_id) ON DELETE CASCADE,
  class_id         uuid NOT NULL REFERENCES share_class(class_id) ON DELETE RESTRICT,
  inv_acct_id      uuid REFERENCES investor_account(inv_acct_id) ON DELETE RESTRICT,
  fee_type         text NOT NULL,
  nav_date         date NOT NULL,
  base_amount      numeric(24,6) NOT NULL,
  rate             numeric(18,10) NOT NULL,
  fee_amount       numeric(24,6) NOT NULL,
  created_at       timestamptz NOT NULL DEFAULT now(),
  CONSTRAINT ck_fee_type CHECK (fee_type IN ('MGMT','PERF'))
);

CREATE INDEX IF NOT EXISTS ix_fee_accrual_class_date ON fee_accrual(tenant_id, class_id, nav_date, fee_type);
CREATE INDEX IF NOT EXISTS ix_fee_acr_inv_date ON fee_accrual(tenant_id, inv_acct_id, nav_date);

CREATE TABLE IF NOT EXISTS hwm_tracker (
  hwm_id           uuid PRIMARY KEY DEFAULT gen_random_uuid(),
  tenant_id        uuid NOT NULL REFERENCES org_tenant(tenant_id) ON DELETE CASCADE,
  inv_acct_id      uuid NOT NULL REFERENCES investor_account(inv_acct_id) ON DELETE CASCADE,
  class_id         uuid NOT NULL REFERENCES share_class(class_id) ON DELETE RESTRICT,
  as_of_date       date NOT NULL,
  hwm_nav_per_unit numeric(24,10) NOT NULL,
  created_at       timestamptz NOT NULL DEFAULT now(),
  CONSTRAINT uq_hwm UNIQUE (tenant_id, inv_acct_id, class_id, as_of_date)
);

CREATE INDEX IF NOT EXISTS ix_hwm_acct_class ON hwm_tracker(tenant_id, inv_acct_id, class_id);

CREATE TABLE IF NOT EXISTS crystallization_event (
  crystal_id     uuid PRIMARY KEY DEFAULT gen_random_uuid(),
  tenant_id      uuid NOT NULL REFERENCES org_tenant(tenant_id) ON DELETE CASCADE,
  class_id       uuid NOT NULL REFERENCES share_class(class_id) ON DELETE RESTRICT,
  period_start   date NOT NULL,
  period_end     date NOT NULL,
  status         text NOT NULL DEFAULT 'DRAFT',
  total_perf_fee numeric(24,6) NOT NULL DEFAULT 0,
  created_at     timestamptz NOT NULL DEFAULT now(),
  CONSTRAINT ck_crystal_status CHECK (status IN ('DRAFT','POSTED','REVERSED')),
  CONSTRAINT uq_crystal_period UNIQUE (tenant_id, class_id, period_start, period_end)
);

CREATE INDEX IF NOT EXISTS ix_crystal_class_period ON crystallization_event(tenant_id, class_id, period_end);

CREATE TABLE IF NOT EXISTS crystallization_alloc (
  crystal_alloc_id uuid PRIMARY KEY DEFAULT gen_random_uuid(),
  tenant_id        uuid NOT NULL REFERENCES org_tenant(tenant_id) ON DELETE CASCADE,
  crystal_id       uuid NOT NULL REFERENCES crystallization_event(crystal_id) ON DELETE CASCADE,
  inv_acct_id      uuid NOT NULL REFERENCES investor_account(inv_acct_id) ON DELETE RESTRICT,
  perf_fee_amount  numeric(24,6) NOT NULL,
  basis_units      numeric(24,10),
  basis_profit     numeric(24,6),
  created_at       timestamptz NOT NULL DEFAULT now(),
  CONSTRAINT uq_crystal_alloc UNIQUE (tenant_id, crystal_id, inv_acct_id)
);

CREATE INDEX IF NOT EXISTS ix_crystal_alloc_crystal ON crystallization_alloc(crystal_id);

-- ============================================================
-- 9) EQUALIZATION (SERIES + EQ_CREDITS; enable per fee_schedule)
-- ============================================================

CREATE TABLE IF NOT EXISTS series (
  series_id              uuid PRIMARY KEY DEFAULT gen_random_uuid(),
  tenant_id              uuid NOT NULL REFERENCES org_tenant(tenant_id) ON DELETE CASCADE,
  class_id               uuid NOT NULL REFERENCES share_class(class_id) ON DELETE RESTRICT,
  series_code            text NOT NULL,
  start_date             date NOT NULL,
  end_date               date,
  series_nav_per_unit_at_open numeric(24,10) NOT NULL,
  created_at             timestamptz NOT NULL DEFAULT now(),
  CONSTRAINT uq_series_code UNIQUE (tenant_id, class_id, series_code)
);

CREATE INDEX IF NOT EXISTS ix_series_class ON series(tenant_id, class_id, start_date);

CREATE TABLE IF NOT EXISTS series_inv_position (
  sip_id        uuid PRIMARY KEY DEFAULT gen_random_uuid(),
  tenant_id     uuid NOT NULL REFERENCES org_tenant(tenant_id) ON DELETE CASCADE,
  series_id     uuid NOT NULL REFERENCES series(series_id) ON DELETE CASCADE,
  inv_acct_id   uuid NOT NULL REFERENCES investor_account(inv_acct_id) ON DELETE RESTRICT,
  units         numeric(24,10) NOT NULL DEFAULT 0,
  hwm_nav_per_unit numeric(24,10),
  created_at    timestamptz NOT NULL DEFAULT now(),
  CONSTRAINT uq_sip UNIQUE (tenant_id, series_id, inv_acct_id)
);

CREATE INDEX IF NOT EXISTS ix_sip_series ON series_inv_position(series_id);

CREATE TABLE IF NOT EXISTS series_roll_event (
  roll_id        uuid PRIMARY KEY DEFAULT gen_random_uuid(),
  tenant_id      uuid NOT NULL REFERENCES org_tenant(tenant_id) ON DELETE CASCADE,
  class_id       uuid NOT NULL REFERENCES share_class(class_id) ON DELETE RESTRICT,
  val_run_id     uuid NOT NULL REFERENCES valuation_run(val_run_id) ON DELETE RESTRICT,
  nav_date       date NOT NULL,
  from_series_id uuid REFERENCES series(series_id) ON DELETE RESTRICT,
  to_series_id   uuid REFERENCES series(series_id) ON DELETE RESTRICT,
  roll_reason    text,
  created_at     timestamptz NOT NULL DEFAULT now(),
  CONSTRAINT uq_roll UNIQUE (tenant_id, class_id, nav_date)
);

CREATE TABLE IF NOT EXISTS equalization_balance (
  eq_bal_id      uuid PRIMARY KEY DEFAULT gen_random_uuid(),
  tenant_id      uuid NOT NULL REFERENCES org_tenant(tenant_id) ON DELETE CASCADE,
  inv_acct_id    uuid NOT NULL REFERENCES investor_account(inv_acct_id) ON DELETE RESTRICT,
  class_id       uuid NOT NULL REFERENCES share_class(class_id) ON DELETE RESTRICT,
  balance_amount numeric(24,6) NOT NULL DEFAULT 0,
  updated_at     timestamptz NOT NULL DEFAULT now(),
  CONSTRAINT uq_eq_bal UNIQUE (tenant_id, inv_acct_id, class_id)
);

CREATE TABLE IF NOT EXISTS equalization_txn (
  eq_txn_id     uuid PRIMARY KEY DEFAULT gen_random_uuid(),
  tenant_id     uuid NOT NULL REFERENCES org_tenant(tenant_id) ON DELETE CASCADE,
  inv_acct_id   uuid NOT NULL REFERENCES investor_account(inv_acct_id) ON DELETE RESTRICT,
  class_id      uuid NOT NULL REFERENCES share_class(class_id) ON DELETE RESTRICT,
  val_run_id    uuid REFERENCES valuation_run(val_run_id) ON DELETE SET NULL,
  nav_date      date NOT NULL,
  amount        numeric(24,6) NOT NULL,
  reason_code   text NOT NULL,
  created_at    timestamptz NOT NULL DEFAULT now()
);

CREATE INDEX IF NOT EXISTS ix_eq_txn_acct_date ON equalization_txn(tenant_id, inv_acct_id, nav_date);

-- ============================================================
-- 10) GL + AUDIT + PERIOD LOCK (minimal)
-- ============================================================

CREATE TABLE IF NOT EXISTS gl_entry (
  entry_id     uuid PRIMARY KEY DEFAULT gen_random_uuid(),
  tenant_id    uuid NOT NULL REFERENCES org_tenant(tenant_id) ON DELETE CASCADE,
  node_id      uuid NOT NULL REFERENCES fund_node(node_id) ON DELETE RESTRICT,
  entry_date   date NOT NULL DEFAULT CURRENT_DATE,
  status       text NOT NULL DEFAULT 'DRAFT',
  source_type  text NOT NULL,
  source_id    uuid,
  reference    text,
  created_at   timestamptz NOT NULL DEFAULT now(),
  CONSTRAINT ck_gl_status CHECK (status IN ('DRAFT','POSTED','REVERSED'))
);

CREATE INDEX IF NOT EXISTS ix_gl_entry_node_date ON gl_entry(tenant_id, node_id, entry_date);

CREATE TABLE IF NOT EXISTS gl_entry_line (
  line_id      uuid PRIMARY KEY DEFAULT gen_random_uuid(),
  tenant_id    uuid NOT NULL REFERENCES org_tenant(tenant_id) ON DELETE CASCADE,
  entry_id     uuid NOT NULL REFERENCES gl_entry(entry_id) ON DELETE CASCADE,
  coa_id       uuid NOT NULL REFERENCES coa_account(coa_id) ON DELETE RESTRICT,
  debit        numeric(24,6) NOT NULL DEFAULT 0,
  credit       numeric(24,6) NOT NULL DEFAULT 0,
  currency     char(3),
  fx_rate      numeric(18,10),
  base_debit   numeric(24,6),
  base_credit  numeric(24,6),
  class_id     uuid REFERENCES share_class(class_id) ON DELETE SET NULL,
  inv_acct_id  uuid REFERENCES investor_account(inv_acct_id) ON DELETE SET NULL,
  created_at   timestamptz NOT NULL DEFAULT now()
);

CREATE INDEX IF NOT EXISTS ix_gl_line_entry ON gl_entry_line(entry_id);
CREATE INDEX IF NOT EXISTS ix_gl_line_coa ON gl_entry_line(tenant_id, coa_id);
CREATE INDEX IF NOT EXISTS ix_gl_line_acct ON gl_entry_line(tenant_id, inv_acct_id);

CREATE TABLE IF NOT EXISTS audit_event (
  audit_id      uuid PRIMARY KEY DEFAULT gen_random_uuid(),
  tenant_id     uuid NOT NULL REFERENCES org_tenant(tenant_id) ON DELETE CASCADE,
  actor_user_id uuid REFERENCES app_user(user_id) ON DELETE SET NULL,
  event_ts      timestamptz NOT NULL DEFAULT now(),
  entity_type   text NOT NULL,
  entity_id     uuid NOT NULL,
  action        text NOT NULL,
  before_json   jsonb,
  after_json    jsonb
);

CREATE INDEX IF NOT EXISTS ix_audit_entity ON audit_event(tenant_id, entity_type, entity_id);
CREATE INDEX IF NOT EXISTS ix_audit_ts ON audit_event(tenant_id, event_ts);

CREATE TABLE IF NOT EXISTS period_lock (
  lock_id      uuid PRIMARY KEY DEFAULT gen_random_uuid(),
  tenant_id    uuid NOT NULL REFERENCES org_tenant(tenant_id) ON DELETE CASCADE,
  node_id      uuid NOT NULL REFERENCES fund_node(node_id) ON DELETE RESTRICT,
  period_start date NOT NULL,
  period_end   date NOT NULL,
  locked_by    uuid REFERENCES app_user(user_id) ON DELETE SET NULL,
  locked_at    timestamptz NOT NULL DEFAULT now(),
  CONSTRAINT uq_period_lock UNIQUE (tenant_id, node_id, period_start, period_end)
);

-- ============================================================
-- End of dump
-- ============================================================
