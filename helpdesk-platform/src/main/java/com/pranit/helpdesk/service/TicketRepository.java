package com.pranit.helpdesk.service;
import com.pranit.helpdesk.domain.Ticket;
import java.util.*;
import org.springframework.data.jpa.repository.JpaRepository;
public interface TicketRepository extends JpaRepository<Ticket, Long> {
  List<Ticket> findByRequesterIdOrderByCreatedAtDesc(Long id);
  List<Ticket> findByAssigneeIdOrderByCreatedAtDesc(Long id);
  List<Ticket> findAllByOrderByCreatedAtDesc();
}