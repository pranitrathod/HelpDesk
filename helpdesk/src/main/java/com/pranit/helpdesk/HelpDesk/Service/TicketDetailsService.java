package com.pranit.helpdesk.HelpDesk.Service;

import com.pranit.helpdesk.HelpDesk.Model.ApiResponse;
import com.pranit.helpdesk.HelpDesk.Model.TicketDTO;
import org.springframework.http.ResponseEntity;

public interface TicketDetailsService {

    public ResponseEntity<ApiResponse> createTicket(TicketDTO req);
}
