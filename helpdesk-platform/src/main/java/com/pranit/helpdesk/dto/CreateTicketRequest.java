package com.pranit.helpdesk.dto;

import com.pranit.helpdesk.domain.Priority;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CreateTicketRequest {
  @NotBlank @Size(max = 200) private String title;
  @NotBlank @Size(max = 4000) private String description;
  @NotNull private Priority priority;
  @DecimalMin("-90.0") @DecimalMax("90.0") private Double latitude;
  @DecimalMin("-180.0") @DecimalMax("180.0") private Double longitude;
  @Size(max = 500) private String address;
}
