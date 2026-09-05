package com.pranit.helpdesk.controller;
import com.pranit.helpdesk.dto.TicketDtos.*;
import com.pranit.helpdesk.service.TicketService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/api/v1/tickets")
public class TicketController {
  private final TicketService service;
  public TicketController(TicketService service) {
    this.service = service;
  }
  @PostMapping
  public ResponseEntity<TicketResponse> create(@RequestHeader("X-User-Id") Long requesterId,
      @Valid @RequestBody CreateTicketRequest request) {
    return ResponseEntity.status(HttpStatus.CREATED).body(service.create(requesterId, request));
  }
  @GetMapping("/{ticketId}")
  public TicketResponse get(@PathVariable Long ticketId) {
    return service.get(ticketId);
  }
  @GetMapping
  public List<TicketResponse> list(@RequestParam(required = false) Long requesterId) {
    return service.list(requesterId);
  }
  @PatchMapping("/{ticketId}/assignee")
  public TicketResponse assign(@PathVariable Long ticketId,
      @RequestHeader("X-User-Id") Long actorId, @Valid @RequestBody AssignTicketRequest request) {
    return service.assign(ticketId, request, actorId);
  }
  @PatchMapping("/{ticketId}/status")
  public TicketResponse status(@PathVariable Long ticketId,
      @RequestHeader("X-User-Id") Long actorId, @Valid @RequestBody UpdateStatusRequest request) {
    return service.updateStatus(ticketId, request, actorId);
  }
  @PostMapping("/{ticketId}/comments")
  public ResponseEntity<CommentResponse> comment(@PathVariable Long ticketId,
      @RequestHeader("X-User-Id") Long authorId, @Valid @RequestBody AddCommentRequest request) {
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(service.addComment(ticketId, request, authorId));
  }
  @GetMapping("/{ticketId}/comments")
  public List<CommentResponse> comments(@PathVariable Long ticketId) {
    return service.comments(ticketId);
  }
  @PostMapping("/{ticketId}/payment")
  public ResponseEntity<PaymentResponse> payment(@PathVariable Long ticketId,
      @RequestHeader("X-User-Id") Long actorId, @Valid @RequestBody CreatePaymentRequest request) {
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(service.createPayment(ticketId, request, actorId));
  }
  @PostMapping("/{ticketId}/payment/refund")
  public PaymentResponse refund(
      @PathVariable Long ticketId, @RequestHeader("X-User-Id") Long actorId) {
    return service.refund(ticketId, actorId);
  }
}
