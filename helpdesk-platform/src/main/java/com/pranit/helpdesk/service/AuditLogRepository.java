package com.pranit.helpdesk.service;
import com.pranit.helpdesk.domain.AuditLog; import org.springframework.data.jpa.repository.JpaRepository;
public interface AuditLogRepository extends JpaRepository<AuditLog,Long>{}