package com.pranit.helpdesk.service;
import com.pranit.helpdesk.domain.*; import org.springframework.data.jpa.repository.JpaRepository; import java.util.*;
interface UserRepository extends JpaRepository<AppUser,Long>{Optional<AppUser> findByEmailIgnoreCase(String email);boolean existsByEmailIgnoreCase(String email);List<AppUser> findByRoleAndActiveTrue(Role role);}
interface TicketRepository extends JpaRepository<Ticket,Long>{List<Ticket> findByRequesterIdOrderByCreatedAtDesc(Long id);List<Ticket> findByAssigneeIdOrderByCreatedAtDesc(Long id);List<Ticket> findAllByOrderByCreatedAtDesc();}
interface CommentRepository extends JpaRepository<TicketComment,Long>{List<TicketComment> findByTicketIdOrderByCreatedAtAsc(Long id);}
interface AuditLogRepository extends JpaRepository<AuditLog,Long>{}