package com.pranit.helpdesk.dto;

import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CommentResponse {
  private final Long id;
  private final Long parentCommentId;
  private final String author;
  private final String message;
  private final Instant createdAt;
}
