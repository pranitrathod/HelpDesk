package com.pranit.productsupport.contracts;
import jakarta.validation.constraints.NotBlank; import jakarta.validation.constraints.NotNull; import jakarta.validation.constraints.Size;
public record CreateTicketRequest(@NotNull Long orderItemId, @NotBlank @Size(max=4000) String issueDescription, TicketPriority priority) {}
