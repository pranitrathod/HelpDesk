package com.pranit.helpdesk.repository;
import com.pranit.helpdesk.domain.TicketLocation; import org.springframework.data.jpa.repository.JpaRepository;
public interface TicketLocationRepository extends JpaRepository<TicketLocation,Long> { }
