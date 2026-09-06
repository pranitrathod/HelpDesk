package com.pranit.helpdesk.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.Instant;

public final class BusinessModuleDtos {
  private BusinessModuleDtos() {}

  public static class CreateCatalogItemRequest {
    @NotBlank @Size(max = 80) private String code;
    @NotBlank @Size(max = 200) private String name;
    @NotBlank @Size(max = 2000) private String description;
    @NotBlank @Size(max = 100) private String category;
    @NotNull @DecimalMin("0.00") private BigDecimal basePrice;
    public String getCode() {
      return code;
    }
    public void setCode(String value) {
      code = value;
    }
    public String getName() {
      return name;
    }
    public void setName(String value) {
      name = value;
    }
    public String getDescription() {
      return description;
    }
    public void setDescription(String value) {
      description = value;
    }
    public String getCategory() {
      return category;
    }
    public void setCategory(String value) {
      category = value;
    }
    public BigDecimal getBasePrice() {
      return basePrice;
    }
    public void setBasePrice(BigDecimal value) {
      basePrice = value;
    }
  }

  public static class CreateArticleRequest {
    @NotBlank @Size(max = 160) private String slug;
    @NotBlank @Size(max = 200) private String title;
    @NotBlank @Size(max = 500) private String summary;
    @NotBlank @Size(max = 12000) private String content;
    private boolean published;
    public String getSlug() {
      return slug;
    }
    public void setSlug(String value) {
      slug = value;
    }
    public String getTitle() {
      return title;
    }
    public void setTitle(String value) {
      title = value;
    }
    public String getSummary() {
      return summary;
    }
    public void setSummary(String value) {
      summary = value;
    }
    public String getContent() {
      return content;
    }
    public void setContent(String value) {
      content = value;
    }
    public boolean isPublished() {
      return published;
    }
    public void setPublished(boolean value) {
      published = value;
    }
  }

  public static class SubmitFeedbackRequest {
    @NotNull private Long ticketId;
    @Min(1) @Max(5) private int rating;
    @Size(max = 2000) private String comment;
    public Long getTicketId() {
      return ticketId;
    }
    public void setTicketId(Long value) {
      ticketId = value;
    }
    public int getRating() {
      return rating;
    }
    public void setRating(int value) {
      rating = value;
    }
    public String getComment() {
      return comment;
    }
    public void setComment(String value) {
      comment = value;
    }
  }

  public static class CatalogItemResponse {
    public Long id;
    public String code;
    public String name;
    public String description;
    public String category;
    public BigDecimal basePrice;
    public boolean active;
    public CatalogItemResponse(Long id, String code, String name, String description,
        String category, BigDecimal basePrice, boolean active) {
      this.id = id;
      this.code = code;
      this.name = name;
      this.description = description;
      this.category = category;
      this.basePrice = basePrice;
      this.active = active;
    }
  }
  public static class ArticleResponse {
    public Long id;
    public String slug;
    public String title;
    public String summary;
    public String content;
    public boolean published;
    public int helpfulVotes;
    public Instant updatedAt;
    public ArticleResponse(Long id, String slug, String title, String summary, String content,
        boolean published, int helpfulVotes, Instant updatedAt) {
      this.id = id;
      this.slug = slug;
      this.title = title;
      this.summary = summary;
      this.content = content;
      this.published = published;
      this.helpfulVotes = helpfulVotes;
      this.updatedAt = updatedAt;
    }
  }
  public static class FeedbackResponse {
    public Long id;
    public Long ticketId;
    public int rating;
    public String comment;
    public Instant submittedAt;
    public FeedbackResponse(
        Long id, Long ticketId, int rating, String comment, Instant submittedAt) {
      this.id = id;
      this.ticketId = ticketId;
      this.rating = rating;
      this.comment = comment;
      this.submittedAt = submittedAt;
    }
  }
}
