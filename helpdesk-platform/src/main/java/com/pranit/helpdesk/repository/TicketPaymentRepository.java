package com.pranit.helpdesk.repository;
import com.pranit.helpdesk.domain.TicketPayment; import org.springframework.data.jpa.repository.JpaRepository; import java.util.Optional;
public interface TicketPaymentRepository extends JpaRepository<TicketPayment,Long> { Optional<TicketPayment> findByTicketId(Long ticketId); }
