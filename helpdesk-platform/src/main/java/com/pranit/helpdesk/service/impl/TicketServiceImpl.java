package com.pranit.helpdesk.service.impl;
import com.pranit.helpdesk.domain.*;
import com.pranit.helpdesk.dto.TicketDtos.*;
import com.pranit.helpdesk.event.*;
import com.pranit.helpdesk.exception.*;
import com.pranit.helpdesk.repository.*;
import com.pranit.helpdesk.service.TicketService;
import java.time.*;
import java.util.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Service
@Transactional
public class TicketServiceImpl implements TicketService {
  private final TicketRepository tickets;
  private final UserRepository users;
  private final CommentRepository comments;
  private final TicketPaymentRepository payments;
  private final TicketLocationRepository locations;
  private final TicketEventPublisher events;
  public TicketServiceImpl(TicketRepository tickets, UserRepository users,
      CommentRepository comments, TicketPaymentRepository payments,
      TicketLocationRepository locations, TicketEventPublisher events) {
    this.tickets = tickets;
    this.users = users;
    this.comments = comments;
    this.payments = payments;
    this.locations = locations;
    this.events = events;
  }
  public TicketResponse create(Long requesterId, CreateTicketRequest r) {
    AppUser requester = user(requesterId);
    Ticket ticket = tickets.save(new Ticket(r.getTitle(), r.getDescription(), r.getPriority(),
        requester, Instant.now().plus(sla(r.getPriority()))));
    if (r.getLatitude() != null && r.getLongitude() != null)
      locations.save(new TicketLocation(ticket, r.getLatitude(), r.getLongitude(), r.getAddress()));
    publish("TICKET_CREATED", ticket, requesterId);
    return dto(ticket);
  }
  @Transactional(readOnly = true)
  public TicketResponse get(Long id) {
    return dto(ticket(id));
  }
  @Transactional(readOnly = true)
  public List<TicketResponse> list(Long requesterId) {
    return (requesterId == null ? tickets.findAllByOrderByCreatedAtDesc()
                                : tickets.findByRequesterIdOrderByCreatedAtDesc(requesterId))
        .stream()
        .map(this::dto)
        .toList();
  }
  public TicketResponse assign(Long id, AssignTicketRequest r, Long actorId) {
    Ticket t = ticket(id);
    rejectClosed(t);
    AppUser agent = user(r.getAgentId());
    if (agent.getRole() != Role.AGENT && agent.getRole() != Role.ADMIN)
      throw new InvalidTicketStateException(
          "Tickets can only be assigned to an agent or administrator");
    t.assign(agent);
    publish("TICKET_ASSIGNED", t, actorId);
    return dto(t);
  }
  public TicketResponse updateStatus(Long id, UpdateStatusRequest r, Long actorId) {
    Ticket t = ticket(id);
    transition(t.getStatus(), r.getStatus());
    t.changeStatus(r.getStatus());
    publish("TICKET_" + r.getStatus(), t, actorId);
    return dto(t);
  }
  public CommentResponse addComment(Long id, AddCommentRequest r, Long authorId) {
    Ticket t = ticket(id);
    AppUser author = user(authorId);
    TicketComment parent = null;
    if (r.getParentCommentId() != null) {
      parent =
          comments.findById(r.getParentCommentId())
              .orElseThrow(() -> new ResourceNotFoundException("Comment", r.getParentCommentId()));
      if (!parent.getTicket().getId().equals(id))
        throw new InvalidTicketStateException("A reply must belong to the same ticket");
    }
    TicketComment comment = comments.save(new TicketComment(t, author, parent, r.getMessage()));
    publish("TICKET_COMMENTED", t, authorId);
    return commentDto(comment);
  }
  @Transactional(readOnly = true)
  public List<CommentResponse> comments(Long id) {
    ticket(id);
    return comments.findByTicketIdOrderByCreatedAtAsc(id).stream().map(this::commentDto).toList();
  }
  public PaymentResponse createPayment(Long id, CreatePaymentRequest r, Long actorId) {
    Ticket t = ticket(id);
    if (payments.findByTicketId(id).isPresent())
      throw new InvalidTicketStateException("A payment already exists for this ticket");
    TicketPayment p = payments.save(
        new TicketPayment(t, r.getAmount(), r.getCurrency(), "HD-" + UUID.randomUUID()));
    p.markAuthorized();
    publish("PAYMENT_AUTHORIZED", t, actorId);
    return paymentDto(p);
  }
  public PaymentResponse refund(Long id, Long actorId) {
    Ticket t = ticket(id);
    TicketPayment p = payments.findByTicketId(id).orElseThrow(
        () -> new ResourceNotFoundException("Payment for ticket", id));
    if (p.getStatus() != PaymentStatus.AUTHORIZED)
      throw new InvalidTicketStateException("Only authorized payments can be refunded");
    p.markRefunded();
    publish("PAYMENT_REFUNDED", t, actorId);
    return paymentDto(p);
  }
  private Ticket ticket(Long id) {
    return tickets.findById(id).orElseThrow(() -> new ResourceNotFoundException("Ticket", id));
  }
  private AppUser user(Long id) {
    return users.findById(id).orElseThrow(() -> new ResourceNotFoundException("User", id));
  }
  private void publish(String type, Ticket t, Long actor) {
    events.publish(new TicketEvent(type, t.getId(), actor));
  }
  private Duration sla(Priority p) {
    return switch (p) {
      case CRITICAL -> Duration.ofHours(4);
      case HIGH -> Duration.ofHours(8);
      case MEDIUM -> Duration.ofHours(24);
      case LOW -> Duration.ofHours(72);
    };
  }
  private void rejectClosed(Ticket t) {
    if (t.getStatus() == TicketStatus.CLOSED)
      throw new InvalidTicketStateException("Closed tickets cannot be changed");
  }
  private void transition(TicketStatus from, TicketStatus to) {
    if (from == TicketStatus.CLOSED || from == to
        || (from == TicketStatus.OPEN && to == TicketStatus.CLOSED))
      throw new InvalidTicketStateException(
          "Transition from " + from + " to " + to + " is not allowed");
  }
  private TicketResponse dto(Ticket t) {
    return new TicketResponse(t.getId(), t.getTitle(), t.getDescription(), t.getPriority(),
        t.getStatus(), t.getRequester().getEmail(),
        t.getAssignee() == null ? null : t.getAssignee().getEmail(), t.getCreatedAt(),
        t.getUpdatedAt(), t.getSlaDueAt());
  }
  private CommentResponse commentDto(TicketComment c) {
    return new CommentResponse(c.getId(),
        c.getParentComment() == null ? null : c.getParentComment().getId(),
        c.getAuthor().getEmail(), c.getMessage(), c.getCreatedAt());
  }
  private PaymentResponse paymentDto(TicketPayment p) {
    return new PaymentResponse(
        p.getId(), p.getAmount(), p.getCurrency(), p.getReference(), p.getStatus());
  }
}
