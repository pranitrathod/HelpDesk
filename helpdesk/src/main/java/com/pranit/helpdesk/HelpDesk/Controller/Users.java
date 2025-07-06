package com.pranit.helpdesk.HelpDesk.Controller;

import com.pranit.helpdesk.HelpDesk.Model.ApiResponse;
import com.pranit.helpdesk.HelpDesk.Model.UserDTO;
import com.pranit.helpdesk.HelpDesk.Service.userDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class Users {

    @Autowired
    userDetailsService userDetails;

    @PostMapping("/createUser")

    public ResponseEntity<ApiResponse> createUser(@RequestBody UserDTO request) {
        return userDetails.createUser(request);
    }
    @GetMapping("/User/{user_id}")
    public ResponseEntity<Object> getUser(@PathVariable("user_id") @RequestBody Long user_id) {

        return userDetails.getUser(user_id);

    }
}
