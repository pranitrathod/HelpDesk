package com.pranit.helpdesk.HelpDesk.Service;

import com.pranit.helpdesk.HelpDesk.Entity.TicketDetails;
import com.pranit.helpdesk.HelpDesk.Model.ApiResponse;
import com.pranit.helpdesk.HelpDesk.Model.TicketDTO;
import com.pranit.helpdesk.HelpDesk.Model.TicketDTOResponse;
import org.springframework.http.ResponseEntity;

public interface TicketDetailsService {

    public ResponseEntity<ApiResponse> createTicket(TicketDTO req);

    public ResponseEntity<TicketDTOResponse> getTicket(TicketDTO req);

    public ResponseEntity<TicketDetails> updateTicket(TicketDTO req);

    public ResponseEntity<TicketDetails> deleteTicket(TicketDTO req);

}
