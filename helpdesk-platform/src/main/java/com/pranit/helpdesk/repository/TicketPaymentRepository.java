package com.pranit.helpdesk.repository;
import com.pranit.helpdesk.domain.TicketPayment;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
public interface TicketPaymentRepository extends JpaRepository<TicketPayment, Long> {
  Optional<TicketPayment> findByTicketId(Long ticketId);
}
