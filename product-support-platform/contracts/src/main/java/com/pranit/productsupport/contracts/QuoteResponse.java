package com.pranit.productsupport.contracts;
import java.time.Instant;
public record QuoteResponse(Long quoteId, Long ticketId, long amountPaise, String currency, String description, String status, Instant expiresAt, Instant createdAt) {}
