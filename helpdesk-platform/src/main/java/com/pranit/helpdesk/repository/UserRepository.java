package com.pranit.helpdesk.repository;
import com.pranit.helpdesk.domain.*; import org.springframework.data.jpa.repository.JpaRepository; import java.util.*;
public interface UserRepository extends JpaRepository<AppUser,Long> { List<AppUser> findByRoleAndActiveTrue(Role role); }
