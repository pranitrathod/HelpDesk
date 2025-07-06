package com.pranit.helpdesk.HelpDesk.Controller;


import com.pranit.helpdesk.HelpDesk.Model.ApiResponse;
import com.pranit.helpdesk.HelpDesk.Model.TicketDTO;
import com.pranit.helpdesk.HelpDesk.Service.TicketDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ticket")
public class Ticket {

    @Autowired
    TicketDetailsService ticketDetails;

    @PostMapping("/createTicket")
    public ResponseEntity<ApiResponse> createTicket(@RequestBody TicketDTO req) {
        return ticketDetails.createTicket(req);
    }

}
