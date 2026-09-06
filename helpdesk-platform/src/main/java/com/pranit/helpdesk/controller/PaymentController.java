package com.pranit.helpdesk.controller;

import com.pranit.helpdesk.dto.TicketDtos.CreatePaymentRequest;
import com.pranit.helpdesk.dto.TicketDtos.PaymentResponse;
import com.pranit.helpdesk.service.TicketService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/** Owns payment authorization and refund operations separately from ticket lifecycle endpoints. */
@RestController
@RequestMapping("/api/v1/tickets/{ticketId}/payment")
public class PaymentController {
  private final TicketService ticketService;

  public PaymentController(TicketService ticketService) {
    this.ticketService = ticketService;
  }

  @PostMapping
  public ResponseEntity<PaymentResponse> authorize(@PathVariable Long ticketId,
      @RequestHeader("X-User-Id") Long actorId, @Valid @RequestBody CreatePaymentRequest request) {
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(ticketService.createPayment(ticketId, request, actorId));
  }

  @PostMapping("/refund")
  public PaymentResponse refund(
      @PathVariable Long ticketId, @RequestHeader("X-User-Id") Long actorId) {
    return ticketService.refund(ticketId, actorId);
  }
}
