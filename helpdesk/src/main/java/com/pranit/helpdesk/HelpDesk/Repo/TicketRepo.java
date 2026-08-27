package com.pranit.helpdesk.HelpDesk.Repo;


import com.pranit.helpdesk.HelpDesk.Entity.TicketDetails;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface TicketRepo extends JpaRepository<TicketDetails, Long> {


    List<TicketDetails> findTicketDetailsByUserId(Long userId);

    TicketDetails findTicketDetailsByTicketId(Long ticketId);
}
