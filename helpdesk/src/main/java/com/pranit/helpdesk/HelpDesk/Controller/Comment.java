package com.pranit.helpdesk.HelpDesk.Controller;

import com.pranit.helpdesk.HelpDesk.Entity.CommentsDetails;
import com.pranit.helpdesk.HelpDesk.Model.CommentDTO;
import com.pranit.helpdesk.HelpDesk.Service.CommentsDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comments")
public class Comment {

    @Autowired
    CommentsDetailsService commentsDetails;

    @PostMapping("/commentThreads")
    public ResponseEntity<Object> comments(@RequestBody CommentDTO comment) {
        return commentsDetails.comments(comment);
    }

    @GetMapping("/getComments/{ticketId}")
    public ResponseEntity<Object> getComments(@PathVariable Long ticketId) {
        return  commentsDetails.getComment(ticketId);
    }

}
