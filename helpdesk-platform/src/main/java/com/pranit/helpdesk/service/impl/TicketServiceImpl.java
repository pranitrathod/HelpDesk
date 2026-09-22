package com.pranit.helpdesk.service.impl;

import com.pranit.helpdesk.domain.AppUser;
import com.pranit.helpdesk.domain.Priority;
import com.pranit.helpdesk.domain.Role;
import com.pranit.helpdesk.domain.Ticket;
import com.pranit.helpdesk.domain.TicketComment;
import com.pranit.helpdesk.domain.TicketPayment;
import com.pranit.helpdesk.domain.PaymentStatus;
import com.pranit.helpdesk.domain.TicketStatus;
import com.pranit.helpdesk.dto.TicketDtos.AddCommentRequest;
import com.pranit.helpdesk.dto.TicketDtos.AssignTicketRequest;
import com.pranit.helpdesk.dto.TicketDtos.CommentResponse;
import com.pranit.helpdesk.dto.TicketDtos.CreatePaymentRequest;
import com.pranit.helpdesk.dto.TicketDtos.CreateTicketRequest;
import com.pranit.helpdesk.dto.TicketDtos.PaymentResponse;
import com.pranit.helpdesk.dto.TicketDtos.TicketResponse;
import com.pranit.helpdesk.dto.TicketDtos.UpdateStatusRequest;
import com.pranit.helpdesk.event.TicketEvent;
import com.pranit.helpdesk.event.TicketEventPublisher;
import com.pranit.helpdesk.exception.InvalidTicketStateException;
import com.pranit.helpdesk.exception.ResourceNotFoundException;
import com.pranit.helpdesk.repository.CommentRepository;
import com.pranit.helpdesk.repository.TicketLocationRepository;
import com.pranit.helpdesk.repository.TicketPaymentRepository;
import com.pranit.helpdesk.repository.TicketRepository;
import com.pranit.helpdesk.repository.UserRepository;
import com.pranit.helpdesk.service.TicketService;
import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class TicketServiceImpl implements TicketService {

  private final TicketRepository tickets;
  private final UserRepository users;
  private final CommentRepository comments;
  private final TicketPaymentRepository payments;
  private final TicketLocationRepository locations;
  private final TicketEventPublisher events;

  @Override
  public TicketResponse create(Long requesterId, CreateTicketRequest request) {
    AppUser requester = user(requesterId);

    Ticket ticket = tickets.save(
        new Ticket(
            request.getTitle(),
            request.getDescription(),
            request.getPriority(),
            requester,
            Instant.now().plus(sla(request.getPriority()))));

    if (request.getLatitude() != null && request.getLongitude() != null) {
      locations.save(
          new TicketLocation(
              ticket,
              request.getLatitude(),
              request.getLongitude(),
              request.getAddress()));
    }

    publish("TICKET_CREATED", ticket, requesterId);
    return dto(ticket);
  }

  @Override
  @Transactional(readOnly = true)
  public TicketResponse get(Long id) {
    return dto(ticket(id));
  }

  @Override
  @Transactional(readOnly = true)
  public List<TicketResponse> list(Long requesterId) {
    return (requesterId == null
            ? tickets.findAllByOrderByCreatedAtDesc()
            : tickets.findByRequesterIdOrderByCreatedAtDesc(requesterId))
        .stream()
        .map(this::dto)
        .toList();
  }

  @Override
  public TicketResponse assign(
      Long id, AssignTicketRequest request, Long actorId) {
    Ticket ticket = ticket(id);
    rejectClosed(ticket);

    AppUser agent = user(request.getAgentId());
    if (agent.getRole() != Role.AGENT && agent.getRole() != Role.ADMIN) {
      throw new InvalidTicketStateException(
          "Tickets can only be assigned to an agent or administrator");
    }

    ticket.assign(agent);
    publish("TICKET_ASSIGNED", ticket, actorId);
    return dto(ticket);
  }

  @Override
  public TicketResponse updateStatus(
      Long id, UpdateStatusRequest request, Long actorId) {
    Ticket ticket = ticket(id);
    transition(ticket.getStatus(), request.getStatus());
    ticket.changeStatus(request.getStatus());
    publish("TICKET_" + request.getStatus(), ticket, actorId);
    return dto(ticket);
  }

  @Override
  public CommentResponse addComment(
      Long id, AddCommentRequest request, Long authorId) {
    Ticket ticket = ticket(id);
    AppUser author = user(authorId);
    TicketComment parent = null;

    if (request.getParentCommentId() != null) {
      parent = comments.findById(request.getParentCommentId())
          .orElseThrow(
              () -> new ResourceNotFoundException(
                  "Comment", request.getParentCommentId()));

      if (!parent.getTicket().getId().equals(id)) {
        throw new InvalidTicketStateException(
            "A reply must belong to the same ticket");
      }
    }

    TicketComment comment =
        comments.save(
            new TicketComment(ticket, author, parent, request.getMessage()));

    publish("TICKET_COMMENTED", ticket, authorId);
    return commentDto(comment);
  }

  @Override
  @Transactional(readOnly = true)
  public List<CommentResponse> comments(Long id) {
    ticket(id);
    return comments.findByTicketIdOrderByCreatedAtAsc(id).stream()
        .map(this::commentDto)
        .toList();
  }

  @Override
  public PaymentResponse createPayment(
      Long id, CreatePaymentRequest request, Long actorId) {
    Ticket ticket = ticket(id);

    if (payments.findByTicketId(id).isPresent()) {
      throw new InvalidTicketStateException(
          "A payment already exists for this ticket");
    }

    TicketPayment payment =
        payments.save(
            new TicketPayment(
                ticket,
                request.getAmount(),
                request.getCurrency(),
                "HD-" + UUID.randomUUID()));

    payment.markAuthorized();
    publish("PAYMENT_AUTHORIZED", ticket, actorId);
    return paymentDto(payment);
  }

  @Override
  public PaymentResponse refund(Long id, Long actorId) {
    Ticket ticket = ticket(id);
    TicketPayment payment =
        payments.findByTicketId(id)
            .orElseThrow(
                () -> new ResourceNotFoundException("Payment for ticket", id));

    if (payment.getStatus() != PaymentStatus.AUTHORIZED) {
      throw new InvalidTicketStateException(
          "Only authorized payments can be refunded");
    }

    payment.markRefunded();
    publish("PAYMENT_REFUNDED", ticket, actorId);
    return paymentDto(payment);
  }

  private Ticket ticket(Long id) {
    return tickets.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Ticket", id));
  }

  private AppUser user(Long id) {
    return users.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("User", id));
  }

  private void publish(String type, Ticket ticket, Long actorId) {
    events.publish(new TicketEvent(type, ticket.getId(), actorId));
  }

  private Duration sla(Priority priority) {
    return switch (priority) {
      case CRITICAL -> Duration.ofHours(4);
      case HIGH -> Duration.ofHours(8);
      case MEDIUM -> Duration.ofHours(24);
      case LOW -> Duration.ofHours(72);
    };
  }

  private void rejectClosed(Ticket ticket) {
    if (ticket.getStatus() == TicketStatus.CLOSED) {
      throw new InvalidTicketStateException(
          "Closed tickets cannot be changed");
    }
  }

  private void transition(TicketStatus from, TicketStatus to) {
    if (from == TicketStatus.CLOSED
        || from == to
        || (from == TicketStatus.OPEN && to == TicketStatus.CLOSED)) {
      throw new InvalidTicketStateException(
          "Transition from " + from + " to " + to + " is not allowed");
    }
  }

  private TicketResponse dto(Ticket ticket) {
    return new TicketResponse(
        ticket.getId(),
        ticket.getTitle(),
        ticket.getDescription(),
        ticket.getPriority(),
        ticket.getStatus(),
        ticket.getRequester().getEmail(),
        ticket.getAssignee() == null ? null : ticket.getAssignee().getEmail(),
        ticket.getCreatedAt(),
        ticket.getUpdatedAt(),
        ticket.getSlaDueAt());
  }

  private CommentResponse commentDto(TicketComment comment) {
    return new CommentResponse(
        comment.getId(),
        comment.getParentComment() == null ? null : comment.getParentComment().getId(),
        comment.getAuthor().getEmail(),
        comment.getMessage(),
        comment.getCreatedAt());
  }

  private PaymentResponse paymentDto(TicketPayment payment) {
    return new PaymentResponse(
        payment.getId(),
        payment.getAmount(),
        payment.getCurrency(),
        payment.getReference(),
        payment.getStatus());
  }
}
