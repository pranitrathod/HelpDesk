package com.pranit.helpdesk.dto;

import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ArticleResponse {
  private final Long id;
  private final String slug;
  private final String title;
  private final String summary;
  private final String content;
  private final boolean published;
  private final int helpfulVotes;
  private final Instant updatedAt;
}
