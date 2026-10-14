package com.pranit.productsupport.contracts;
import jakarta.validation.constraints.NotBlank; import jakarta.validation.constraints.Size;
public record CreateProductRequest(@NotBlank @Size(max=80) String sku, @NotBlank @Size(max=160) String name, @Size(max=2000) String description) {}
