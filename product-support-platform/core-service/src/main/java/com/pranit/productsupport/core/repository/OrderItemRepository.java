package com.pranit.productsupport.core.repository;
import com.pranit.productsupport.core.domain.OrderItem; import org.springframework.data.jpa.repository.JpaRepository;
public interface OrderItemRepository extends JpaRepository<OrderItem,Long>{ }
