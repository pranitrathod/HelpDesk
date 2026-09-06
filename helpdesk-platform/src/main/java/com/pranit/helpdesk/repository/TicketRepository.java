package com.pranit.helpdesk.repository;
import com.pranit.helpdesk.domain.Ticket;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
public interface TicketRepository extends JpaRepository<Ticket, Long> {
  List<Ticket> findByRequesterIdOrderByCreatedAtDesc(Long requesterId);
  List<Ticket> findAllByOrderByCreatedAtDesc();
}
