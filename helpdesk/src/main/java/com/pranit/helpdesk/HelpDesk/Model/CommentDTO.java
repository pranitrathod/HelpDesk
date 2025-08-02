package com.pranit.helpdesk.HelpDesk.Model;


import lombok.Data;

@Data
public class CommentDTO {
    private Long ticket_id;
    private Long user_id;
    private String comment;
}
