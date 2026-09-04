package com.pranit.helpdesk.service;
import com.pranit.helpdesk.domain.Ticket; import org.springframework.data.jpa.repository.JpaRepository; import java.util.*;
public interface TicketRepository extends JpaRepository<Ticket,Long>{List<Ticket> findByRequesterIdOrderByCreatedAtDesc(Long id);List<Ticket> findByAssigneeIdOrderByCreatedAtDesc(Long id);List<Ticket> findAllByOrderByCreatedAtDesc();}