package com.pranit.helpdesk.controller;
import com.pranit.helpdesk.dto.BusinessModuleDtos.*;
import com.pranit.helpdesk.service.ServiceCatalogService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
/** Lets customers discover supported services and lets operations retire unavailable offerings. */
@RestController
@RequestMapping("/api/v1/service-catalog")
public class ServiceCatalogController {
  private final ServiceCatalogService service;
  public ServiceCatalogController(ServiceCatalogService service) {
    this.service = service;
  }
  @PostMapping
  public ResponseEntity<CatalogItemResponse> create(
      @Valid @RequestBody CreateCatalogItemRequest request) {
    return ResponseEntity.status(HttpStatus.CREATED).body(service.create(request));
  }
  @GetMapping
  public List<CatalogItemResponse> listActive() {
    return service.listActive();
  }
  @PatchMapping("/{itemId}/retire")
  public CatalogItemResponse retire(@PathVariable Long itemId) {
    return service.retire(itemId);
  }
}
