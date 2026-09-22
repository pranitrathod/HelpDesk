package com.pranit.helpdesk.controller;

import com.pranit.helpdesk.dto.TicketDtos.AddCommentRequest;
import com.pranit.helpdesk.dto.TicketDtos.AssignTicketRequest;
import com.pranit.helpdesk.dto.TicketDtos.CommentResponse;
import com.pranit.helpdesk.dto.TicketDtos.CreatePaymentRequest;
import com.pranit.helpdesk.dto.TicketDtos.CreateTicketRequest;
import com.pranit.helpdesk.dto.TicketDtos.PaymentResponse;
import com.pranit.helpdesk.dto.TicketDtos.TicketResponse;
import com.pranit.helpdesk.dto.TicketDtos.UpdateStatusRequest;
import com.pranit.helpdesk.service.TicketService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/tickets")
@RequiredArgsConstructor
public class TicketController {

  private final TicketService ticketService;

  @PostMapping
  public ResponseEntity<TicketResponse> create(
      @RequestHeader("X-User-Id") Long requesterId,
      @Valid @RequestBody CreateTicketRequest request) {
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(ticketService.create(requesterId, request));
  }

  @GetMapping("/{ticketId}")
  public TicketResponse get(@PathVariable Long ticketId) {
    return ticketService.get(ticketId);
  }

  @GetMapping
  public List<TicketResponse> list(@RequestParam(required = false) Long requesterId) {
    return ticketService.list(requesterId);
  }

  @PatchMapping("/{ticketId}/assignee")
  public TicketResponse assign(
      @PathVariable Long ticketId,
      @RequestHeader("X-User-Id") Long actorId,
      @Valid @RequestBody AssignTicketRequest request) {
    return ticketService.assign(ticketId, request, actorId);
  }

  @PatchMapping("/{ticketId}/status")
  public TicketResponse status(
      @PathVariable Long ticketId,
      @RequestHeader("X-User-Id") Long actorId,
      @Valid @RequestBody UpdateStatusRequest request) {
    return ticketService.updateStatus(ticketId, request, actorId);
  }

  @PostMapping("/{ticketId}/comments")
  public ResponseEntity<CommentResponse> comment(
      @PathVariable Long ticketId,
      @RequestHeader("X-User-Id") Long authorId,
      @Valid @RequestBody AddCommentRequest request) {
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(ticketService.addComment(ticketId, request, authorId));
  }

  @GetMapping("/{ticketId}/comments")
  public List<CommentResponse> comments(@PathVariable Long ticketId) {
    return ticketService.comments(ticketId);
  }

  @PostMapping("/{ticketId}/payment")
  public ResponseEntity<PaymentResponse> payment(
      @PathVariable Long ticketId,
      @RequestHeader("X-User-Id") Long actorId,
      @Valid @RequestBody CreatePaymentRequest request) {
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(ticketService.createPayment(ticketId, request, actorId));
  }

  @PostMapping("/{ticketId}/payment/refund")
  public PaymentResponse refund(
      @PathVariable Long ticketId, @RequestHeader("X-User-Id") Long actorId) {
    return ticketService.refund(ticketId, actorId);
  }
}
