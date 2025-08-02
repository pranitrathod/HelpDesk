package com.pranit.helpdesk.HelpDesk.Repo;

import com.pranit.helpdesk.HelpDesk.Entity.CommentsDetails;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepo extends JpaRepository<CommentsDetails, Integer> {

//    public List<CommentsDetails> findByCommentId(String commentId);

     List<CommentsDetails> findByTicket_TicketId(Long ticketId);
}
