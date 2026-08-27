package com.pranit.helpdesk.repository;
import com.pranit.helpdesk.entity.Comment; import org.springframework.data.jpa.repository.JpaRepository; import java.util.List;
public interface CommentRepository extends JpaRepository<Comment,Long>{List<Comment> findByTicketIdOrderByCreatedAtAsc(Long id);}
