package com.pranit.helpdesk.service;
import com.pranit.helpdesk.dto.BusinessModuleDtos.CatalogItemResponse;
import com.pranit.helpdesk.dto.BusinessModuleDtos.CreateCatalogItemRequest;
import java.util.List;
public interface ServiceCatalogService {
  CatalogItemResponse create(CreateCatalogItemRequest request);
  List<CatalogItemResponse> listActive();
  CatalogItemResponse retire(Long itemId);
}
