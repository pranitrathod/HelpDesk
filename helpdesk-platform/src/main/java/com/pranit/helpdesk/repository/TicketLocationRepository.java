package com.pranit.helpdesk.repository;
import com.pranit.helpdesk.domain.TicketLocation;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
public interface TicketLocationRepository extends JpaRepository<TicketLocation, Long> {
  Optional<TicketLocation> findByTicketId(Long ticketId);
}
