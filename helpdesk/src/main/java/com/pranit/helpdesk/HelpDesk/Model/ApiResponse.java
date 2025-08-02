package com.pranit.helpdesk.HelpDesk.Model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse {

    private String message;
    private boolean sucess;
    private LocalDateTime timeStamp;
    private int statusCode;

}


