package com.pranit.helpdesk.controller;
import com.pranit.helpdesk.dto.BusinessModuleDtos.*;
import com.pranit.helpdesk.service.CustomerFeedbackService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
/** Captures post-resolution CSAT feedback for reporting and service improvement. */
@RestController
@RequestMapping("/api/v1/customer-feedback")
public class CustomerFeedbackController {
  private final CustomerFeedbackService service;
  public CustomerFeedbackController(CustomerFeedbackService service) {
    this.service = service;
  }
  @PostMapping
  public ResponseEntity<FeedbackResponse> submit(@RequestHeader("X-User-Id") Long customerId,
      @Valid @RequestBody SubmitFeedbackRequest request) {
    return ResponseEntity.status(HttpStatus.CREATED).body(service.submit(customerId, request));
  }
  @GetMapping("/ticket/{ticketId}")
  public List<FeedbackResponse> findForTicket(@PathVariable Long ticketId) {
    return service.findForTicket(ticketId);
  }
}
