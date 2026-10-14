package com.pranit.productsupport.contracts;
import java.time.Instant; import java.util.List;
public record OrderResponse(Long orderId, String customerEmail, String status, Instant createdAt, List<OrderLineResponse> items) {}
