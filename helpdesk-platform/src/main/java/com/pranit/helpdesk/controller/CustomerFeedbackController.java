package com.pranit.helpdesk.controller;

import com.pranit.helpdesk.dto.FeedbackResponse;
import com.pranit.helpdesk.dto.SubmitFeedbackRequest;
import com.pranit.helpdesk.service.CustomerFeedbackService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/** Captures post-resolution CSAT feedback for reporting and service improvement. */
@RestController
@RequestMapping("/api/v1/customer-feedback")
@RequiredArgsConstructor
public class CustomerFeedbackController {

  private final CustomerFeedbackService customerFeedbackService;

  @PostMapping
  public ResponseEntity<FeedbackResponse> submit(
      @RequestHeader("X-User-Id") Long customerId,
      @Valid @RequestBody SubmitFeedbackRequest request) {
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(customerFeedbackService.submit(customerId, request));
  }

  @GetMapping("/ticket/{ticketId}")
  public List<FeedbackResponse> findForTicket(@PathVariable Long ticketId) {
    return customerFeedbackService.findForTicket(ticketId);
  }
}
