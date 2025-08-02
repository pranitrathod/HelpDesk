package com.pranit.helpdesk.HelpDesk.Repo;

import com.pranit.helpdesk.HelpDesk.Entity.UserDetails;
import org.apache.catalina.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepo extends JpaRepository<UserDetails, Long> {

      boolean existsById(Long userId);

//      UserDetails findUserDetailsByUserId(Long userId);


      UserDetails findUserDetailsById(Long Id);


      //   @Query("select UserDetails.userId from UserDetails where UserDetails.userId=:userId")
//      UserDetails findUserDetailsByUserId(int userId);

}
