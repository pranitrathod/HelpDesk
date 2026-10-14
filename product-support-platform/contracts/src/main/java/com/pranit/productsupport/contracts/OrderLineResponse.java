package com.pranit.productsupport.contracts;
public record OrderLineResponse(Long orderItemId, Long productId, String sku, String productName, int quantity) {}
