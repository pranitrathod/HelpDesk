package com.pranit.helpdesk.HelpDesk.Model;

import lombok.Data;
import java.time.LocalDate;

@Data
public class TicketDTOResponse {

    private String user_id;
    private Long ticket_Id;
    private String status;
    private String user_name;
    private LocalDate createdAt;
    private String updatedAt;
    private String title;
    private String description;
}
