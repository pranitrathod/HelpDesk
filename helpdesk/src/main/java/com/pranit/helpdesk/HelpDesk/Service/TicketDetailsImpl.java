package com.pranit.helpdesk.HelpDesk.Service;

import com.pranit.helpdesk.HelpDesk.Entity.UserDetails;
import com.pranit.helpdesk.HelpDesk.Model.ApiResponse;
import com.pranit.helpdesk.HelpDesk.Model.TicketDTO;
import com.pranit.helpdesk.HelpDesk.Repo.TicketRepo;
import com.pranit.helpdesk.HelpDesk.Entity.TicketDetails;


import com.pranit.helpdesk.HelpDesk.Repo.UserRepo;
import jakarta.persistence.Entity;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;


@Service
public class TicketDetailsImpl implements TicketDetailsService {

    @Autowired
    TicketRepo ticketRepo;
    @Autowired
    private UserRepo userRepo;

    @Override
    public ResponseEntity<ApiResponse> createTicket(TicketDTO req){
        int userDetails=userRepo.findUserDetailsByUserId(req.getUserId());

        System.out.println(userDetails.getUserId());
        //Add exception and here later for tickets
        TicketDetails newTicket = new TicketDetails();

        newTicket.setTitle(req.getTitle());
        newTicket.setDescription(req.getDescription());
        newTicket.setUser(userDetails);
        ticketRepo.save(newTicket);

        return new ResponseEntity<>(new ApiResponse("Ticket Created!",true, LocalDateTime.now(),200), HttpStatus.OK);

    }
}
