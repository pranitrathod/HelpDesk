package com.pranit.helpdesk.service;
import com.pranit.helpdesk.domain.TicketComment;
import java.util.*;
import org.springframework.data.jpa.repository.JpaRepository;
public interface CommentRepository extends JpaRepository<TicketComment, Long> {
  List<TicketComment> findByTicketIdOrderByCreatedAtAsc(Long id);
}