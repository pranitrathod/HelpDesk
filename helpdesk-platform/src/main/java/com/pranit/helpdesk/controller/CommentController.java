package com.pranit.helpdesk.controller;

import com.pranit.helpdesk.dto.TicketDtos.AddCommentRequest;
import com.pranit.helpdesk.dto.TicketDtos.CommentResponse;
import com.pranit.helpdesk.service.TicketService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/** Handles the collaboration stream for a ticket, including replies in a comment thread. */
@RestController
@RequestMapping("/api/v1/tickets/{ticketId}/comments")
public class CommentController {
  private final TicketService ticketService;

  public CommentController(TicketService ticketService) {
    this.ticketService = ticketService;
  }

  @PostMapping
  public ResponseEntity<CommentResponse> addComment(@PathVariable Long ticketId,
      @RequestHeader("X-User-Id") Long authorId, @Valid @RequestBody AddCommentRequest request) {
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(ticketService.addComment(ticketId, request, authorId));
  }

  @GetMapping
  public List<CommentResponse> listComments(@PathVariable Long ticketId) {
    return ticketService.comments(ticketId);
  }
}
