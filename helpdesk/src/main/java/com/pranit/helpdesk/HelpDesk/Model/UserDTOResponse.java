package com.pranit.helpdesk.HelpDesk.Model;

import lombok.Data;

@Data
public class UserDTOResponse {
    private String userName;
    private String email;
    private String location;
    private Long userId;
}
