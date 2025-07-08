package com.pranit.helpdesk.HelpDesk.Service;

import com.pranit.helpdesk.HelpDesk.Entity.UserDetails;
import com.pranit.helpdesk.HelpDesk.Model.ApiResponse;
import com.pranit.helpdesk.HelpDesk.Model.UserDTO;
import com.pranit.helpdesk.HelpDesk.Model.UserDTOResponse;
import com.pranit.helpdesk.HelpDesk.Repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class UserDetailsImpl implements userDetailsService {

    @Autowired
    UserRepo userRepoo;

    @Override
    public ResponseEntity<ApiResponse> createUser(UserDTO request) {
        UserDetails user = new UserDetails();

        user.setUserName(request.getUserName());
        user.setEmail(request.getEmail());
        user.setLocation(request.getLocation());
        user.setUserId(request.getUserId());

        if (userRepoo.existsByUserId(request.getUserId())) {
            throw new IllegalArgumentException("User Already exists!");
        }
            userRepoo.save(user);
            return new ResponseEntity<>(new ApiResponse("User Created!",true,LocalDateTime.now(),200), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Object> getUser(Long user_id) {
        UserDTOResponse resp=new UserDTOResponse();

        UserDetails user=userRepoo.findUserDetailsByUserId(user_id);

        resp.setUserId(user.getUserId());
        resp.setUserName(user.getUserName());
        resp.setEmail(user.getEmail());
        resp.setLocation(user.getLocation());

        return new ResponseEntity<>(resp,HttpStatus.OK);

    }



}
