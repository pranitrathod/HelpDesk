package com.pranit.helpdesk.repository;
import com.pranit.helpdesk.entity.Ticket; import com.pranit.helpdesk.model.TicketStatus; import org.springframework.data.jpa.repository.JpaRepository; import java.util.List;
public interface TicketRepository extends JpaRepository<Ticket,Long>{List<Ticket> findByCreatedByIdOrderByCreatedAtDesc(Long id);List<Ticket> findByAssignedToIdOrderByCreatedAtDesc(Long id);long countByStatus(TicketStatus status);}
