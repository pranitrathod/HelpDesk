package com.pranit.helpdesk.HelpDesk.Model;

import lombok.Data;

@Data
public class UserDTO {

    private int id;
    private String userName;
    private String email;
    private String location;
    private int userId;
}
