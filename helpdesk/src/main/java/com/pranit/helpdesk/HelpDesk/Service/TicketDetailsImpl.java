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

        TicketDetails ticketDetails=ticketRepo.findTicketDetailsByUserId(req.getUserId());
        UserDetails userDetails=userRepo.findUserDetailsByUserId(req.getUserId());
        if(ticketDetails==null)
        {
        TicketDetails newTicket = new TicketDetails();
        newTicket.setTitle(req.getTitle());
        newTicket.setDescription(req.getDescription());
        newTicket.setUser(userDetails);
        newTicket.setCreatedAt(LocalDate.now());
        newTicket.setStatus(req.getStatus());
        ticketRepo.save(newTicket);
        }
        else{
            if(ticketDetails!=null){
            throw new ResourceNotFoundException("Ticket Already Raised and its Status is "+ ticketDetails.getStatus());}
        }
        return new ResponseEntity<>(new ApiResponse("Ticket Created!",true, LocalDateTime.now(),200), HttpStatus.OK);
    }
}

//We have to implement 1 things :
//1.If ticket already existing filter based on Ticket Id and user_id
//2.If ticket is present check the status and tell the use it open/inProgress/Closed
//3.If Check why the hell user_id is getting 1 into the db