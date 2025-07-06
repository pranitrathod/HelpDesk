package com.pranit.helpdesk.HelpDesk.Service;


import com.pranit.helpdesk.HelpDesk.Model.ApiResponse;
import com.pranit.helpdesk.HelpDesk.Model.UserDTO;
import org.springframework.http.ResponseEntity;

public interface userDetailsService {
    ResponseEntity<ApiResponse> createUser(UserDTO request);
<<<<<<< HEAD
    ResponseEntity<Object> getUser(Long user_id);
=======
>>>>>>> 4a8f453cc0503b9731703fb75a4258797dc478d2
}
