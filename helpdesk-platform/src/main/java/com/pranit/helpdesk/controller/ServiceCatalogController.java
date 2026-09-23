package com.pranit.helpdesk.controller;

import com.pranit.helpdesk.dto.CatalogItemResponse;
import com.pranit.helpdesk.dto.CreateCatalogItemRequest;
import com.pranit.helpdesk.service.ServiceCatalogService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/** Lets customers discover supported services and lets operations retire unavailable offerings. */
@RestController
@RequestMapping("/api/v1/service-catalog")
@RequiredArgsConstructor
public class ServiceCatalogController {

  private final ServiceCatalogService serviceCatalogService;

  @PostMapping
  public ResponseEntity<CatalogItemResponse> create(
      @Valid @RequestBody CreateCatalogItemRequest request) {
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(serviceCatalogService.create(request));
  }

  @GetMapping
  public List<CatalogItemResponse> listActive() {
    return serviceCatalogService.listActive();
  }

  @PatchMapping("/{itemId}/retire")
  public CatalogItemResponse retire(@PathVariable Long itemId) {
    return serviceCatalogService.retire(itemId);
  }
}
