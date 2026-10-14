package com.pranit.productsupport.core.api;
import com.pranit.productsupport.contracts.*; import com.pranit.productsupport.core.service.CatalogOrderService; import jakarta.validation.Valid; import java.util.List; import org.springframework.http.HttpStatus; import org.springframework.security.access.prepost.PreAuthorize; import org.springframework.security.oauth2.jwt.Jwt; import org.springframework.security.core.annotation.AuthenticationPrincipal; import org.springframework.web.bind.annotation.*;
@RestController @RequestMapping("/internal/v1") public class CatalogOrderController {private final CatalogOrderService service;public CatalogOrderController(CatalogOrderService s){service=s;}
 @GetMapping("/products") public List<ProductResponse> products(){return service.listProducts();}
 @PostMapping("/admin/products") @PreAuthorize("hasRole('ADMIN')") @ResponseStatus(HttpStatus.CREATED) public ProductResponse createProduct(@Valid @RequestBody CreateProductRequest r){return service.createProduct(r);}
 @PostMapping("/admin/orders") @PreAuthorize("hasRole('ADMIN')") @ResponseStatus(HttpStatus.CREATED) public OrderResponse createOrder(@Valid @RequestBody CreateOrderRequest r){return service.createOrder(r);}
 @GetMapping("/orders/me") public List<OrderResponse> orders(@AuthenticationPrincipal Jwt jwt){return service.myOrders(Long.valueOf(jwt.getSubject()));}
}
