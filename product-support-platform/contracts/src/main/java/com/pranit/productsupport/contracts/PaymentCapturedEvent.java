package com.pranit.productsupport.contracts;
import java.time.Instant;
public record PaymentCapturedEvent(Long paymentId, Long quoteId, Long ticketId, long amountPaise, String currency, Instant occurredAt) {}
