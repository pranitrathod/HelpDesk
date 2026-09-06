package com.pranit.helpdesk.repository;
import com.pranit.helpdesk.domain.CustomerFeedback;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
public interface CustomerFeedbackRepository extends JpaRepository<CustomerFeedback, Long> {
  boolean existsByTicketId(Long ticketId);
  List<CustomerFeedback> findByTicketId(Long ticketId);
}
