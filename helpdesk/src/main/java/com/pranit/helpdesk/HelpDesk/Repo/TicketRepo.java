package com.pranit.helpdesk.HelpDesk.Repo;


import com.pranit.helpdesk.HelpDesk.Entity.TicketDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketRepo extends JpaRepository<TicketDetails, Long> {

}
