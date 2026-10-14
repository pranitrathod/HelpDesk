package com.pranit.productsupport.contracts;
public record AuthResponse(String accessToken, String tokenType, long expiresInSeconds, Long userId, String email, String role) {}
