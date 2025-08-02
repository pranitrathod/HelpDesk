package com.pranit.helpdesk.HelpDesk.Controller;


import com.pranit.helpdesk.HelpDesk.Entity.TicketDetails;
import com.pranit.helpdesk.HelpDesk.Model.ApiResponse;
import com.pranit.helpdesk.HelpDesk.Model.TicketDTO;


import com.pranit.helpdesk.HelpDesk.Model.TicketDTOResponse;
import com.pranit.helpdesk.HelpDesk.Service.TicketDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ticket")
public class Ticket {

    @Autowired
    TicketDetailsService ticketDetails;

    @PostMapping("/createTicket")
    public ResponseEntity<ApiResponse> createTicket(@RequestBody TicketDTO req) {
        return ticketDetails.createTicket(req);
    }

    @GetMapping("getTickets")
    public ResponseEntity<TicketDTOResponse> getTickets(@RequestBody TicketDTO req) {
        return ticketDetails.getTicket(req);
    }

    @PutMapping("updateTicket")
    public ResponseEntity<TicketDetails> updateTicket(@RequestBody TicketDTO req) {
        return ticketDetails.updateTicket(req);
    }

    @DeleteMapping("/deletedTicket")
    public ResponseEntity<TicketDetails> deleteTicket(@RequestBody TicketDTO req) {
        return ticketDetails.deleteTicket(req);
    }

}
