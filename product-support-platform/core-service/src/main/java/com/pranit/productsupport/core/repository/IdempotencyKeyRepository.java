package com.pranit.productsupport.core.repository;
import com.pranit.productsupport.core.domain.IdempotencyKey; import java.util.Optional; import org.springframework.data.jpa.repository.JpaRepository;
public interface IdempotencyKeyRepository extends JpaRepository<IdempotencyKey,Long>{ Optional<IdempotencyKey> findByUser_IdAndKey(Long userId,String key); }
