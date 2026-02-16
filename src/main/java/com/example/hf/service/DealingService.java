package com.example.hf.service;

import com.example.hf.dto.CreateOrderLegRequest;
import com.example.hf.dto.CreateOrderRequest;
import com.example.hf.entity.DealingOrder;
import com.example.hf.entity.DealingOrderLeg;
import com.example.hf.repository.DealingOrderLegRepository;
import com.example.hf.repository.DealingOrderRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class DealingService {

  private final DealingOrderRepository dealingOrderRepository;
  private final DealingOrderLegRepository dealingOrderLegRepository;

  public DealingService(DealingOrderRepository dealingOrderRepository, DealingOrderLegRepository dealingOrderLegRepository) {
    this.dealingOrderRepository = dealingOrderRepository;
    this.dealingOrderLegRepository = dealingOrderLegRepository;
  }

  @Transactional
  public DealingOrder createOrder(CreateOrderRequest req) {
    var o = new DealingOrder();
    o.setOrderId(UUID.randomUUID());
    o.setTenantId(req.tenantId());
    o.setClassId(req.classId());
    o.setOrderType(req.orderType());
    o.setOrderStatus("SUBMITTED");
    o.setTradeDate(req.tradeDate() == null ? LocalDate.now() : req.tradeDate());
    o.setAmountCcy(req.amountCcy() == null ? "USD" : req.amountCcy());
    o.setAmount(req.amount());
    o.setUnits(req.units());
    o.setSourceInvAcctId(req.sourceInvAcctId());
    o.setTargetInvAcctId(req.targetInvAcctId());
    o.setNotes(req.notes());
    o.setCreatedAt(OffsetDateTime.now());
    var saved = dealingOrderRepository.save(o);

    if (req.legs() != null && !req.legs().isEmpty()) {
      persistLegs(saved.getOrderId(), req.tenantId(), req.legs());
    }
    return saved;
  }

  private void persistLegs(UUID orderId, UUID tenantId, List<CreateOrderLegRequest> legs) {
    for (var l : legs) {
      var leg = new DealingOrderLeg();
      leg.setLegId(UUID.randomUUID());
      leg.setTenantId(tenantId);
      leg.setOrderId(orderId);
      leg.setLegDir(l.legDir());
      leg.setFromClassId(l.fromClassId());
      leg.setToClassId(l.toClassId());
      leg.setFromInvAcctId(l.fromInvAcctId());
      leg.setToInvAcctId(l.toInvAcctId());
      leg.setReqAmount(l.reqAmount());
      leg.setReqUnits(l.reqUnits());
      leg.setCurrency(l.currency());
      leg.setCreatedAt(OffsetDateTime.now());
      dealingOrderLegRepository.save(leg);
    }
  }

  /**
   * Pricing is NAV-date dependent and will set units/amount based on class_nav.nav_per_unit.
   * This is a stub entrypoint for a real pricing engine.
   */
  @Transactional
  public DealingOrder priceOrder(UUID orderId, LocalDate navDate) {
    var o = dealingOrderRepository.findById(orderId).orElseThrow(() -> new IllegalArgumentException("Order not found"));
    o.setNavDate(navDate);
    o.setOrderStatus("PRICED");
    return dealingOrderRepository.save(o);
  }

  @Transactional
  public DealingOrder settleOrder(UUID orderId, LocalDate settleDate) {
    var o = dealingOrderRepository.findById(orderId).orElseThrow(() -> new IllegalArgumentException("Order not found"));
    o.setSettleDate(settleDate == null ? LocalDate.now() : settleDate);
    o.setOrderStatus("SETTLED");
    return dealingOrderRepository.save(o);
  }

  @Transactional
  public DealingOrder cancelOrder(UUID orderId) {
    var o = dealingOrderRepository.findById(orderId).orElseThrow(() -> new IllegalArgumentException("Order not found"));
    o.setOrderStatus("CANCELLED");
    return dealingOrderRepository.save(o);
  }
}
