package com.pranit.helpdesk.service.impl;

import com.pranit.helpdesk.domain.ServiceCatalogItem;
import com.pranit.helpdesk.dto.CatalogItemResponse;
import com.pranit.helpdesk.dto.CreateCatalogItemRequest;
import com.pranit.helpdesk.exception.ConflictException;
import com.pranit.helpdesk.exception.ResourceNotFoundException;
import com.pranit.helpdesk.repository.ServiceCatalogRepository;
import com.pranit.helpdesk.service.ServiceCatalogService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ServiceCatalogServiceImpl implements ServiceCatalogService {

  private final ServiceCatalogRepository repository;

  @Override
  public CatalogItemResponse create(CreateCatalogItemRequest request) {
    if (repository.existsByCodeIgnoreCase(request.getCode())) {
      throw new ConflictException(
          "A catalog item already uses code " + request.getCode());
    }

    return response(repository.save(
        new ServiceCatalogItem(
            request.getCode(),
            request.getName(),
            request.getDescription(),
            request.getCategory(),
            request.getBasePrice())));
  }

  @Override
  @Transactional(readOnly = true)
  public List<CatalogItemResponse> listActive() {
    return repository.findByActiveTrueOrderByCategoryAscNameAsc().stream()
        .map(this::response)
        .toList();
  }

  @Override
  public CatalogItemResponse retire(Long itemId) {
    ServiceCatalogItem item =
        repository.findById(itemId)
            .orElseThrow(() -> new ResourceNotFoundException(
                "Catalog item", itemId));

    item.retire();
    return response(item);
  }

  private CatalogItemResponse response(ServiceCatalogItem item) {
    return new CatalogItemResponse(
        item.getId(),
        item.getCode(),
        item.getName(),
        item.getDescription(),
        item.getCategory(),
        item.getBasePrice(),
        item.isActive());
  }
}
