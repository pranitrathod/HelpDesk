package com.pranit.helpdesk.repository;
import com.pranit.helpdesk.domain.Ticket; import org.springframework.data.jpa.repository.JpaRepository; import java.util.List;
public interface TicketRepository extends JpaRepository<Ticket, Long> { List<Ticket> findByRequesterIdOrderByCreatedAtDesc(Long requesterId); List<Ticket> findAllByOrderByCreatedAtDesc(); }
