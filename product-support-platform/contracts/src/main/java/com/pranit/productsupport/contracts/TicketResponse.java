package com.pranit.productsupport.contracts;
import java.time.Instant;
public record TicketResponse(Long ticketId, Long customerId, Long orderItemId, Long productId, String productName, String issueDescription, TicketStatus status, TicketPriority priority, Long assignedTo, Instant createdAt, Instant updatedAt, long version) {}
