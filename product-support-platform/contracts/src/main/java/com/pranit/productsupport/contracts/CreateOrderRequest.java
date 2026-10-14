package com.pranit.productsupport.contracts;
import jakarta.validation.Valid; import jakarta.validation.constraints.Email; import jakarta.validation.constraints.NotEmpty; import jakarta.validation.constraints.NotNull; import java.util.List;
public record CreateOrderRequest(@NotNull @Email String customerEmail, @NotEmpty List<@Valid OrderLineRequest> items, String status) {}
