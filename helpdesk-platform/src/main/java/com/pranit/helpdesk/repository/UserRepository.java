package com.pranit.helpdesk.repository;
import com.pranit.helpdesk.domain.*;
import java.util.*;
import org.springframework.data.jpa.repository.JpaRepository;
public interface UserRepository extends JpaRepository<AppUser, Long> {
  List<AppUser> findByRoleAndActiveTrue(Role role);
}
