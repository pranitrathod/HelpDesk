package com.pranit.productsupport.core.repository;
import com.pranit.productsupport.core.domain.TicketComment; import java.util.List; import org.springframework.data.jpa.repository.JpaRepository;
public interface TicketCommentRepository extends JpaRepository<TicketComment,Long>{ List<TicketComment> findByTicket_IdOrderByCreatedAtAsc(Long ticketId); }
