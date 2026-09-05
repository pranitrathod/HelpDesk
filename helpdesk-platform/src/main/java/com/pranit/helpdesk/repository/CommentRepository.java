package com.pranit.helpdesk.repository;
import com.pranit.helpdesk.domain.TicketComment; import org.springframework.data.jpa.repository.JpaRepository; import java.util.List;
public interface CommentRepository extends JpaRepository<TicketComment,Long> { List<TicketComment> findByTicketIdOrderByCreatedAtAsc(Long ticketId); }
