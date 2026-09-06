package com.pranit.helpdesk.controller;

import com.pranit.helpdesk.dto.TicketDtos.LocationResponse;
import com.pranit.helpdesk.service.TicketService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/** Provides a focused API for map and field-service clients. */
@RestController
@RequestMapping("/api/v1/tickets/{ticketId}/location")
public class TicketLocationController {
  private final TicketService ticketService;

  public TicketLocationController(TicketService ticketService) {
    this.ticketService = ticketService;
  }

  @GetMapping
  public LocationResponse getLocation(@PathVariable Long ticketId) {
    return ticketService.location(ticketId);
  }
}
