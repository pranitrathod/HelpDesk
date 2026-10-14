package com.pranit.productsupport.contracts;
public record ProductResponse(Long productId, String sku, String name, String description, boolean active) {}
