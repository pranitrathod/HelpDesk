package com.pranit.helpdesk.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public final class BusinessModuleDtos {

  private BusinessModuleDtos() {}

  @Getter
  @Setter
  @NoArgsConstructor
  public static class CreateCatalogItemRequest {
    @NotBlank @Size(max = 80) private String code;
    @NotBlank @Size(max = 200) private String name;
    @NotBlank @Size(max = 2000) private String description;
    @NotBlank @Size(max = 100) private String category;
    @NotNull @DecimalMin("0.00") private BigDecimal basePrice;
  }

  @Getter
  @Setter
  @NoArgsConstructor
  public static class CreateArticleRequest {
    @NotBlank @Size(max = 160) private String slug;
    @NotBlank @Size(max = 200) private String title;
    @NotBlank @Size(max = 500) private String summary;
    @NotBlank @Size(max = 12000) private String content;
    private boolean published;
  }

  @Getter
  @Setter
  @NoArgsConstructor
  public static class SubmitFeedbackRequest {
    @NotNull private Long ticketId;
    @Min(1) @Max(5) private int rating;
    @Size(max = 2000) private String comment;
  }

  @Getter
  @AllArgsConstructor
  public static class CatalogItemResponse {
    private final Long id;
    private final String code;
    private final String name;
    private final String description;
    private final String category;
    private final BigDecimal basePrice;
    private final boolean active;
  }

  @Getter
  @AllArgsConstructor
  public static class ArticleResponse {
    private final Long id;
    private final String slug;
    private final String title;
    private final String summary;
    private final String content;
    private final boolean published;
    private final int helpfulVotes;
    private final Instant updatedAt;
  }

  @Getter
  @AllArgsConstructor
  public static class FeedbackResponse {
    private final Long id;
    private final Long ticketId;
    private final int rating;
    private final String comment;
    private final Instant submittedAt;
  }
}
