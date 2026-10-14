package com.pranit.productsupport.contracts;
import java.time.Instant;
public record TicketStatusChangedEvent(Long ticketId, String oldStatus, String newStatus, Long actorId, Instant occurredAt) {}
