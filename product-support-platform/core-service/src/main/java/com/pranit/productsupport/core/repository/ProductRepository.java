package com.pranit.productsupport.core.repository;
import com.pranit.productsupport.core.domain.Product; import java.util.List; import org.springframework.data.jpa.repository.JpaRepository;
public interface ProductRepository extends JpaRepository<Product,Long>{ List<Product> findByActiveTrueOrderByNameAsc(); boolean existsBySkuIgnoreCase(String sku); }
