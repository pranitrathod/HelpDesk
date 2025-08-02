package com.pranit.helpdesk.HelpDesk.Service;

import com.pranit.helpdesk.HelpDesk.Entity.CommentsDetails;
import com.pranit.helpdesk.HelpDesk.Entity.TicketDetails;
import com.pranit.helpdesk.HelpDesk.Entity.UserDetails;
import com.pranit.helpdesk.HelpDesk.ExceptionHandler.ResourceNotFoundException;
import com.pranit.helpdesk.HelpDesk.Model.CommentDTO;
import com.pranit.helpdesk.HelpDesk.Model.CommentDTOResponse;
import com.pranit.helpdesk.HelpDesk.Repo.CommentRepo;
import com.pranit.helpdesk.HelpDesk.Repo.TicketRepo;
import com.pranit.helpdesk.HelpDesk.Repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class CommentsDetailsImpl implements CommentsDetailsService {

    @Autowired
    CommentRepo commentRepo;

    @Autowired
    TicketRepo ticketRepo;

    @Autowired
    UserRepo userRepo;

    public ResponseEntity<Object> comments(CommentDTO req)
    {
        CommentsDetails cmt=new CommentsDetails();
//        TicketDetails ticket_id=new TicketDetails();
//        UserDetails user_id=new UserDetails();

        TicketDetails checkTicketId=ticketRepo.findTicketDetailsByTicketId(req.getTicket_id());
        UserDetails checkUserId=userRepo.findUserDetailsById(req.getUser_id());

        if(checkUserId==null && checkTicketId==null){
            throw new ResourceNotFoundException("Ticket/User not found!");
        }
        if(checkTicketId==null){
            throw new ResourceNotFoundException("Ticket not found!");
        }
        if(checkUserId==null){
            throw new ResourceNotFoundException("User not found!");
        }

//        ticket_id.setTicketId(req.getTicket_id());
        cmt.setTicket(checkTicketId);
//        user_id.setUserId(req.getUser_id());0
        cmt.setUser(checkUserId);
        cmt.setMessage(req.getComment());
        cmt.setCreated_at(LocalDateTime.now());

        commentRepo.save(cmt);

        return ResponseEntity.ok(cmt);

    }

    public ResponseEntity<Object> getComment(Long ticketId)
    {
        TicketDetails checkTicketId=ticketRepo.findTicketDetailsByTicketId(ticketId);

        if(checkTicketId==null){
            throw new ResourceNotFoundException("Ticket not found!");
        }
        List<CommentsDetails> commentDetails = commentRepo.findByTicket_TicketId(ticketId);
        List<CommentDTOResponse> commentDTOResponse=new ArrayList<>();
        for (CommentsDetails singleObjectComment : commentDetails) {
            CommentDTOResponse commentResponse=new CommentDTOResponse();

            commentResponse.setCommentId(singleObjectComment.getComment_id());
            commentResponse.setTicketId(singleObjectComment.getTicket().getTicketId());
            commentResponse.setComment(singleObjectComment.getMessage());
            commentResponse.setCreateAt(singleObjectComment.getCreated_at());

            commentResponse.setUserId(singleObjectComment.getUser().getId());
            commentDTOResponse.add(commentResponse);
        }
        return ResponseEntity.ok(commentDTOResponse);
    }

}

//Next we have to get based on User
//JWT ROLE BASED ACCESSED TOO
//REAL TIME CHATTING with Agent
//Also Add PDF file to server

