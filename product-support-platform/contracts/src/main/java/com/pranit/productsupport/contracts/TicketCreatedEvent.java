package com.pranit.productsupport.contracts;
import java.time.Instant;
public record TicketCreatedEvent(Long ticketId, Long customerId, Long orderItemId, Long productId, String productName, String issueDescription, Instant occurredAt) {}
