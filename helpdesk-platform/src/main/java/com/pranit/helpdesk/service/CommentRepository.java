package com.pranit.helpdesk.service;
import com.pranit.helpdesk.domain.TicketComment; import org.springframework.data.jpa.repository.JpaRepository; import java.util.*;
public interface CommentRepository extends JpaRepository<TicketComment,Long>{List<TicketComment> findByTicketIdOrderByCreatedAtAsc(Long id);}