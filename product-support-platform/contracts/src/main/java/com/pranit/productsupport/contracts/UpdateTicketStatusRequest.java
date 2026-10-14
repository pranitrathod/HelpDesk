package com.pranit.productsupport.contracts;
import jakarta.validation.constraints.NotNull; import jakarta.validation.constraints.Size;
public record UpdateTicketStatusRequest(@NotNull TicketStatus status, @Size(max=1000) String note) {}
