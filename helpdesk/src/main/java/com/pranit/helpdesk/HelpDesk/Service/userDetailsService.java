package com.pranit.helpdesk.HelpDesk.Service;


import com.pranit.helpdesk.HelpDesk.Model.ApiResponse;
import com.pranit.helpdesk.HelpDesk.Model.UserDTO;
import org.springframework.http.ResponseEntity;

public interface userDetailsService {
    
    ResponseEntity<ApiResponse> createUser(UserDTO request);
    
}
