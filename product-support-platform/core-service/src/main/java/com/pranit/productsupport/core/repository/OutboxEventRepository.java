package com.pranit.productsupport.core.repository;
import com.pranit.productsupport.core.domain.OutboxEvent; import java.util.List; import org.springframework.data.jpa.repository.JpaRepository;
public interface OutboxEventRepository extends JpaRepository<OutboxEvent,String>{ List<OutboxEvent> findTop50ByStatusOrderByCreatedAtAsc(String status); }
