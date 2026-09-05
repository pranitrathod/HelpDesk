package com.pranit.helpdesk.service;
import com.pranit.helpdesk.dto.TicketDtos.*;
import java.util.List;
public interface TicketService {
  TicketResponse create(Long requesterId, CreateTicketRequest request);
  TicketResponse get(Long ticketId);
  List<TicketResponse> list(Long requesterId);
  TicketResponse assign(Long ticketId, AssignTicketRequest request, Long actorId);
  TicketResponse updateStatus(Long ticketId, UpdateStatusRequest request, Long actorId);
  CommentResponse addComment(Long ticketId, AddCommentRequest request, Long authorId);
  List<CommentResponse> comments(Long ticketId);
  PaymentResponse createPayment(Long ticketId, CreatePaymentRequest request, Long actorId);
  PaymentResponse refund(Long ticketId, Long actorId);
}
