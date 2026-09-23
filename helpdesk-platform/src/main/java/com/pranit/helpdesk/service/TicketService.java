package com.pranit.helpdesk.service;

import com.pranit.helpdesk.dto.AddCommentRequest;
import com.pranit.helpdesk.dto.AssignTicketRequest;
import com.pranit.helpdesk.dto.CommentResponse;
import com.pranit.helpdesk.dto.CreateTicketPaymentRequest;
import com.pranit.helpdesk.dto.CreateTicketRequest;
import com.pranit.helpdesk.dto.TicketPaymentResponse;
import com.pranit.helpdesk.dto.TicketResponse;
import com.pranit.helpdesk.dto.UpdateStatusRequest;
import java.util.List;

public interface TicketService {
  TicketResponse create(Long requesterId, CreateTicketRequest request);
  TicketResponse get(Long ticketId);
  List<TicketResponse> list(Long requesterId);
  TicketResponse assign(Long ticketId, AssignTicketRequest request, Long actorId);
  TicketResponse updateStatus(Long ticketId, UpdateStatusRequest request, Long actorId);
  CommentResponse addComment(Long ticketId, AddCommentRequest request, Long authorId);
  List<CommentResponse> comments(Long ticketId);
  TicketPaymentResponse createPayment(
      Long ticketId, CreateTicketPaymentRequest request, Long actorId);
  TicketPaymentResponse refund(Long ticketId, Long actorId);
}
