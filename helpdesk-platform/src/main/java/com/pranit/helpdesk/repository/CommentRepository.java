package com.pranit.helpdesk.repository;
import com.pranit.helpdesk.domain.TicketComment;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
public interface CommentRepository extends JpaRepository<TicketComment, Long> {
  List<TicketComment> findByTicketIdOrderByCreatedAtAsc(Long ticketId);
}
