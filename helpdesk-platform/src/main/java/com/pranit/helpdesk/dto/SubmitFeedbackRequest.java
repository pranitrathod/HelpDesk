package com.pranit.helpdesk.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SubmitFeedbackRequest {
  @NotNull private Long ticketId;
  @Min(1) @Max(5) private int rating;
  @Size(max = 2000) private String comment;
}
