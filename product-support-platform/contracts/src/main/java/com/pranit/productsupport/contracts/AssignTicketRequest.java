package com.pranit.productsupport.contracts;
import jakarta.validation.constraints.NotNull;
public record AssignTicketRequest(@NotNull Long supportAgentId) {}
