package com.pranit.helpdesk.dto;

import com.pranit.helpdesk.domain.Priority;
import com.pranit.helpdesk.domain.PaymentStatus;
import com.pranit.helpdesk.domain.TicketStatus;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public final class TicketDtos {

  private TicketDtos() {}

  @Getter
  @Setter
  @NoArgsConstructor
  public static class CreateTicketRequest {
    @NotBlank @Size(max = 200) private String title;
    @NotBlank @Size(max = 4000) private String description;
    @NotNull private Priority priority;
    @DecimalMin("-90.0") @DecimalMax("90.0") private Double latitude;
    @DecimalMin("-180.0") @DecimalMax("180.0") private Double longitude;
    @Size(max = 500) private String address;
  }

  @Getter
  @Setter
  @NoArgsConstructor
  public static class AssignTicketRequest {
    @NotNull private Long agentId;
  }

  @Getter
  @Setter
  @NoArgsConstructor
  public static class UpdateStatusRequest {
    @NotNull private TicketStatus status;
  }

  @Getter
  @Setter
  @NoArgsConstructor
  public static class AddCommentRequest {
    @NotBlank @Size(max = 2000) private String message;
    private Long parentCommentId;
  }

  @Getter
  @Setter
  @NoArgsConstructor
  public static class CreatePaymentRequest {
    @NotNull @DecimalMin("0.01") private BigDecimal amount;
    @NotBlank @Pattern(regexp = "[A-Z]{3}") private String currency;
  }

  @Getter
  @AllArgsConstructor
  public static class TicketResponse {
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

  @Getter
  @AllArgsConstructor
  public static class CommentResponse {
    private final Long id;
    private final Long parentCommentId;
    private final String author;
    private final String message;
    private final Instant createdAt;
  }

  @Getter
  @AllArgsConstructor
  public static class PaymentResponse {
    private final Long id;
    private final BigDecimal amount;
    private final String currency;
    private final String reference;
    private final PaymentStatus status;
  }
}
