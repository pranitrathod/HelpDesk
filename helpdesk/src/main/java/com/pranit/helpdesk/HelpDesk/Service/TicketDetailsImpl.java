package com.pranit.helpdesk.HelpDesk.Service;

import com.pranit.helpdesk.HelpDesk.Entity.UserDetails;
import com.pranit.helpdesk.HelpDesk.ExceptionHandler.ResourceNotFoundException;
import com.pranit.helpdesk.HelpDesk.Model.ApiResponse;
import com.pranit.helpdesk.HelpDesk.Model.TicketDTO;
import com.pranit.helpdesk.HelpDesk.Repo.TicketRepo;
import com.pranit.helpdesk.HelpDesk.Entity.TicketDetails;
import com.pranit.helpdesk.HelpDesk.Repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.LocalDateTime;


@Service
public class TicketDetailsImpl implements TicketDetailsService {

    @Autowired
    TicketRepo ticketRepo;
    @Autowired
    private UserRepo userRepo;

    @Override
    public ResponseEntity<ApiResponse> createTicket(TicketDTO req){

        UserDetails userDetails=userRepo.findUserDetailsByUserId(req.getUserId());
        if(userDetails!=null){

        TicketDetails newTicket = new TicketDetails();
        newTicket.setTitle(req.getTitle());
        newTicket.setDescription(req.getDescription());
        newTicket.setUser(userDetails);
        newTicket.setCreateAt(LocalDate.now());

        ticketRepo.save(newTicket);

        }else{
            throw new ResourceNotFoundException("Ticket not Found!");
        }
        return new ResponseEntity<>(new ApiResponse("Ticket Created!",true, LocalDateTime.now(),200), HttpStatus.OK);
    }
}
