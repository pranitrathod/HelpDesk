package com.pranit.helpdesk.dto;

import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class FeedbackResponse {
  private final Long id;
  private final Long ticketId;
  private final int rating;
  private final String comment;
  private final Instant submittedAt;
}
