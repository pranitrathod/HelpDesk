package com.pranit.helpdesk.dto;

import com.pranit.helpdesk.domain.Priority;
import com.pranit.helpdesk.domain.TicketStatus;
import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TicketResponse {
  private final Long id;
  private final String title;
  private final String description;
  private final Priority priority;
  private final TicketStatus status;
  private final String requester;
  private final String assignee;
  private final Instant createdAt;
  private final Instant updatedAt;
  private final Instant slaDueAt;
}
