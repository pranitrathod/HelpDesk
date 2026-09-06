package com.pranit.helpdesk.service.impl;
import com.pranit.helpdesk.domain.ServiceCatalogItem;
import com.pranit.helpdesk.dto.BusinessModuleDtos.*;
import com.pranit.helpdesk.exception.*;
import com.pranit.helpdesk.repository.ServiceCatalogRepository;
import com.pranit.helpdesk.service.ServiceCatalogService;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Service
@Transactional
public class ServiceCatalogServiceImpl implements ServiceCatalogService {
  private final ServiceCatalogRepository repository;
  public ServiceCatalogServiceImpl(ServiceCatalogRepository repository) {
    this.repository = repository;
  }
  public CatalogItemResponse create(CreateCatalogItemRequest request) {
    if (repository.existsByCodeIgnoreCase(request.getCode()))
      throw new ConflictException("A catalog item already uses code " + request.getCode());
    return response(repository.save(new ServiceCatalogItem(request.getCode(), request.getName(),
        request.getDescription(), request.getCategory(), request.getBasePrice())));
  }
  @Transactional(readOnly = true)
  public List<CatalogItemResponse> listActive() {
    return repository.findByActiveTrueOrderByCategoryAscNameAsc()
        .stream()
        .map(this::response)
        .toList();
  }
  public CatalogItemResponse retire(Long itemId) {
    ServiceCatalogItem item = repository.findById(itemId).orElseThrow(
        () -> new ResourceNotFoundException("Catalog item", itemId));
    item.retire();
    return response(item);
  }
  private CatalogItemResponse response(ServiceCatalogItem item) {
    return new CatalogItemResponse(item.getId(), item.getCode(), item.getName(),
        item.getDescription(), item.getCategory(), item.getBasePrice(), item.isActive());
  }
}
