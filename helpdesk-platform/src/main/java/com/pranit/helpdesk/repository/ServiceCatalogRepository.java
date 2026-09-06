package com.pranit.helpdesk.repository;
import com.pranit.helpdesk.domain.ServiceCatalogItem;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
public interface ServiceCatalogRepository extends JpaRepository<ServiceCatalogItem, Long> {
  boolean existsByCodeIgnoreCase(String code);
  List<ServiceCatalogItem> findByActiveTrueOrderByCategoryAscNameAsc();
}
