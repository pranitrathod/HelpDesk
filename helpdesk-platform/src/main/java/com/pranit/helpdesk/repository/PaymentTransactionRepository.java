package com.pranit.helpdesk.repository;

import com.pranit.helpdesk.domain.PaymentTransaction;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentTransactionRepository extends JpaRepository<PaymentTransaction, Long> {
  Optional<PaymentTransaction> findByIdempotencyKey(String idempotencyKey);
  Optional<PaymentTransaction> findByProviderOrderId(String providerOrderId);
}
