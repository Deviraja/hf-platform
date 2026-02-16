package com.example.hf.service;

import com.example.hf.dto.CreateFundEdgeRequest;
import com.example.hf.dto.CreateFundNodeRequest;
import com.example.hf.dto.CreateFundRequest;
import com.example.hf.dto.CreateShareClassRequest;
import com.example.hf.entity.Fund;
import com.example.hf.entity.FundEdge;
import com.example.hf.entity.FundNode;
import com.example.hf.entity.ShareClass;
import com.example.hf.repository.FundEdgeRepository;
import com.example.hf.repository.FundNodeRepository;
import com.example.hf.repository.FundRepository;
import com.example.hf.repository.ShareClassRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.UUID;

@Service
public class FundOnboardingService {

  private final FundRepository fundRepository;
  private final FundNodeRepository fundNodeRepository;
  private final FundEdgeRepository fundEdgeRepository;
  private final ShareClassRepository shareClassRepository;

  public FundOnboardingService(
      FundRepository fundRepository,
      FundNodeRepository fundNodeRepository,
      FundEdgeRepository fundEdgeRepository,
      ShareClassRepository shareClassRepository
  ) {
    this.fundRepository = fundRepository;
    this.fundNodeRepository = fundNodeRepository;
    this.fundEdgeRepository = fundEdgeRepository;
    this.shareClassRepository = shareClassRepository;
  }

  @Transactional
  public Fund createFund(CreateFundRequest req) {
    var f = new Fund();
    f.setFundId(UUID.randomUUID());
    f.setTenantId(req.tenantId());
    f.setFundCode(req.fundCode());
    f.setFundName(req.fundName());
    f.setBaseCcy(req.baseCcy());
    f.setInceptionDate(req.inceptionDate());
    f.setStatus("ACTIVE");
    f.setCreatedAt(OffsetDateTime.now());
    return fundRepository.save(f);
  }

  @Transactional
  public FundNode addNode(CreateFundNodeRequest req) {
    var n = new FundNode();
    n.setNodeId(UUID.randomUUID());
    n.setTenantId(req.tenantId());
    n.setFundId(req.fundId());
    n.setNodeType(req.nodeType());
    n.setNodeName(req.nodeName());
    n.setNodeCcy(req.nodeCcy());
    n.setStatus("ACTIVE");
    n.setCreatedAt(OffsetDateTime.now());
    return fundNodeRepository.save(n);
  }

  @Transactional
  public FundEdge addEdge(CreateFundEdgeRequest req) {
    var e = new FundEdge();
    e.setEdgeId(UUID.randomUUID());
    e.setTenantId(req.tenantId());
    e.setParentNodeId(req.parentNodeId());
    e.setChildNodeId(req.childNodeId());
    e.setEconomicPct(req.economicPct());
    e.setEffectiveFrom(req.effectiveFrom() == null ? java.time.LocalDate.now() : req.effectiveFrom());
    e.setEffectiveTo(req.effectiveTo());
    e.setCreatedAt(OffsetDateTime.now());
    return fundEdgeRepository.save(e);
  }

  @Transactional
  public ShareClass addShareClass(CreateShareClassRequest req) {
    var c = new ShareClass();
    c.setClassId(UUID.randomUUID());
    c.setTenantId(req.tenantId());
    c.setNodeId(req.nodeId());
    c.setClassCode(req.classCode());
    c.setClassName(req.className());
    c.setClassCcy(req.classCcy());
    c.setDealingCutoffTime(req.dealingCutoffTime());
    c.setSettlementDays(req.settlementDays() == null ? 2 : req.settlementDays());
    c.setIsAccumulating(req.isAccumulating() == null ? Boolean.TRUE : req.isAccumulating());
    c.setStatus("ACTIVE");
    c.setCreatedAt(OffsetDateTime.now());
    return shareClassRepository.save(c);
  }
}
