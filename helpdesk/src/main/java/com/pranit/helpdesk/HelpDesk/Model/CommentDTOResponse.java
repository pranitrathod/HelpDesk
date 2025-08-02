package com.pranit.helpdesk.HelpDesk.Model;


import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class CommentDTOResponse {

private int userId;
private String comment;
private Long commentId;
private Long ticketId;
private LocalDateTime createAt;


}
