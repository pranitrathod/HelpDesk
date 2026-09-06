package com.pranit.helpdesk.service;
import com.pranit.helpdesk.domain.*; import org.springframework.data.jpa.repository.JpaRepository; import java.util.*;
public interface UserRepository extends JpaRepository<AppUser,Long>{Optional<AppUser> findByEmailIgnoreCase(String email);boolean existsByEmailIgnoreCase(String email);List<AppUser> findByRoleAndActiveTrue(Role role);}