package com.pranit.productsupport.core.repository;
import com.pranit.productsupport.core.domain.AppUser; import java.util.Optional; import org.springframework.data.jpa.repository.JpaRepository;
public interface AppUserRepository extends JpaRepository<AppUser,Long>{ Optional<AppUser> findByEmail(String email); boolean existsByEmail(String email); }
