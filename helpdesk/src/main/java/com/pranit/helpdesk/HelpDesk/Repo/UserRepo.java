package com.pranit.helpdesk.HelpDesk.Repo;

import com.pranit.helpdesk.HelpDesk.Entity.UserDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepo extends JpaRepository<UserDetails, Long> {

      boolean existsByUserId(int userId);

//      @Query("select UserDetails.userId from UserDetails where UserDetails.userId=:userId")
      UserDetails findUserDetailsByUserId(int userId);


}
