package com.pranit.productsupport.contracts;
public record CreatePaymentResponse(Long paymentId, Long quoteId, String provider, String providerOrderId, long amountPaise, String currency, String status, String checkoutKeyId) {}
