package com.pranit.helpdesk.HelpDesk.Service;


import com.pranit.helpdesk.HelpDesk.Model.CommentDTO;
import org.springframework.http.ResponseEntity;

public interface CommentsDetailsService {

    ResponseEntity<Object> comments(CommentDTO comment);

    ResponseEntity<Object> getComment(Long ticketId);
}
