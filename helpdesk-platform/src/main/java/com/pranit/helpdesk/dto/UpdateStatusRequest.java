package com.pranit.helpdesk.dto;

import com.pranit.helpdesk.domain.TicketStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UpdateStatusRequest {
  @NotNull private TicketStatus status;
}
