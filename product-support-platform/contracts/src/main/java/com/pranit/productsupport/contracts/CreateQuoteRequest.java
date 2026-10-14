package com.pranit.productsupport.contracts;
import jakarta.validation.constraints.Min; import jakarta.validation.constraints.Max; import jakarta.validation.constraints.NotBlank; import jakarta.validation.constraints.Size;
public record CreateQuoteRequest(@Min(100) @Max(100000000) long amountPaise, @NotBlank @Size(max=500) String description) {}
