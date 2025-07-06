package com.pranit.helpdesk.HelpDesk.Model;

import com.pranit.helpdesk.HelpDesk.Entity.UserDetails;
import lombok.Data;

@Data
public class TicketDTO {
    private int userId;
    private String description;
    private String title;
}
