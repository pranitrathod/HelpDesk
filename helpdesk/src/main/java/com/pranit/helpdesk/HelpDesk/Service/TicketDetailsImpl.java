package com.pranit.helpdesk.HelpDesk.Service;


import com.pranit.helpdesk.HelpDesk.Entity.UserDetails;
import com.pranit.helpdesk.HelpDesk.ExceptionHandler.ResourceNotFoundException;
import com.pranit.helpdesk.HelpDesk.Model.ApiResponse;

import com.pranit.helpdesk.HelpDesk.Model.TicketDTO;

import com.pranit.helpdesk.HelpDesk.Model.TicketDTOResponse;
import com.pranit.helpdesk.HelpDesk.Repo.TicketRepo;
import com.pranit.helpdesk.HelpDesk.Entity.TicketDetails;
import com.pranit.helpdesk.HelpDesk.Repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


import java.time.LocalDate;
import java.time.LocalDateTime;

import java.util.List;


@Service
public class TicketDetailsImpl implements TicketDetailsService {

    @Autowired
    private TicketRepo ticketRepo;
    @Autowired
    private UserRepo userRepo;

    @Override
    public ResponseEntity<ApiResponse> createTicket(TicketDTO req){

        List<TicketDetails> ticketsDetails= ticketRepo.findTicketDetailsByUserId(req.getUserId());

        if(ticketsDetails.size()==0){
        UserDetails userDetails=userRepo.findUserDetailsById(req.getUserId());
        if(userDetails!=null){

            TicketDetails newTicket = new TicketDetails();
            newTicket.setTitle(req.getTitle());
            newTicket.setDescription(req.getDescription());
            newTicket.setUser(userDetails);
            newTicket.setCreatedAt(LocalDate.now());
            newTicket.setStatus(req.getStatus());
            ticketRepo.save(newTicket);
        }
       else{
           throw new ResourceNotFoundException("User not found!");
        }
        }
        else{
            if(ticketsDetails!=null){
            throw new IllegalArgumentException("Ticket Already Raised ");}
        }
        return new ResponseEntity<>(new ApiResponse("Ticket Created!",true, LocalDateTime.now(),200), HttpStatus.OK);
    }

    public ResponseEntity<TicketDTOResponse> getTicket(TicketDTO req){
//        TicketDetails ticketDetails= new TicketDetails();
        List<TicketDetails> ticketsDetails= ticketRepo.findTicketDetailsByUserId(req.getUserId());
        TicketDTOResponse ticketDTOResponse=new TicketDTOResponse();

        if(ticketsDetails.isEmpty()){
            throw new ResourceNotFoundException("Ticket not found!");
        }
        else{
            for(TicketDetails ticketDetails:ticketsDetails){
                if(ticketDetails==null){
                    throw new ResourceNotFoundException("Ticket not found!");
                }
                if(ticketDetails.getTitle()!=null)
                {
                    ticketDTOResponse.setTitle(ticketDetails.getTitle());
                }
                if(ticketDetails.getDescription()!=null)
                {
                    ticketDTOResponse.setDescription(ticketDetails.getDescription());
                }
                if(ticketDetails.getUser()!=null)
                {
                    ticketDTOResponse.setUser_name(ticketDetails.getUser().getUserName());
                }
                if(ticketDetails.getStatus()!=null)
                {
                    ticketDTOResponse.setStatus(ticketDetails.getStatus());
                }
                if(ticketDetails.getCreatedAt()!=null)
                {
                    ticketDTOResponse.setTicket_Id(ticketDetails.getTicketId());
                }
                if(ticketDetails.getCreatedAt()!=null)
                    ticketDTOResponse.setCreatedAt(ticketDetails.getCreatedAt());
            }
        }
        return ResponseEntity.ok(ticketDTOResponse);
    }

    public ResponseEntity<TicketDetails> updateTicket(TicketDTO req){
        TicketDetails ticketsDetails= ticketRepo.findTicketDetailsByTicketId(req.getTicket_Id());

        if(ticketsDetails==null){
            throw new ResourceNotFoundException("Ticket not found!");
        }
        else {

           if(ticketsDetails.getDescription()!=null){
               ticketsDetails.setTitle(ticketsDetails.getDescription());
           }
           if(req.getStatus()==null){
               ticketsDetails.setStatus("CLOSED");
           }
           if(req.getStatus()!=null && ticketsDetails.getStatus().toLowerCase().equals("closed")){
               ticketsDetails.setStatus(req.getStatus());
            }
           ticketsDetails.setUpdatedAt(LocalDateTime.now());
        }
        ticketRepo.save(ticketsDetails);
        return ResponseEntity.ok(ticketsDetails);
    }

    public ResponseEntity<TicketDetails> deleteTicket(TicketDTO req){
        TicketDetails ticketDetails= ticketRepo.findTicketDetailsByTicketId(req.getTicket_Id());
        if(ticketDetails==null){
            throw new ResourceNotFoundException("Ticket not found!");
        }
        else {
            ticketRepo.delete(ticketDetails);
            return ResponseEntity.ok(ticketDetails);
        }
    }


}