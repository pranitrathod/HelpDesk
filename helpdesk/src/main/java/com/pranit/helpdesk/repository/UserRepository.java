package com.pranit.helpdesk.repository;
import com.pranit.helpdesk.entity.User; import org.springframework.data.jpa.repository.JpaRepository; import java.util.Optional;
public interface UserRepository extends JpaRepository<User,Long>{Optional<User> findByUsername(String username);Optional<User> findByEmail(String email);}
