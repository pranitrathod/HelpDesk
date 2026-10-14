package com.pranit.productsupport.contracts;
import jakarta.validation.constraints.Email; import jakarta.validation.constraints.NotBlank; import jakarta.validation.constraints.Size;
public record RegisterRequest(@NotBlank @Size(max=120) String fullName, @NotBlank @Email @Size(max=254) String email, @NotBlank @Size(min=12,max=128) String password) {}
