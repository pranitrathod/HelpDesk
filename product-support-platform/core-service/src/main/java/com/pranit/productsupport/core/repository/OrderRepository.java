package com.pranit.productsupport.core.repository;
import com.pranit.productsupport.core.domain.PurchaseOrder; import java.util.List; import org.springframework.data.jpa.repository.JpaRepository;
public interface OrderRepository extends JpaRepository<PurchaseOrder,Long>{ List<PurchaseOrder> findByCustomer_IdOrderByCreatedAtDesc(Long customerId); }
