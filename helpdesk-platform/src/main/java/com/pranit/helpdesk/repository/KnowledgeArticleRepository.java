package com.pranit.helpdesk.repository;
import com.pranit.helpdesk.domain.KnowledgeArticle;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
public interface KnowledgeArticleRepository extends JpaRepository<KnowledgeArticle, Long> {
  Optional<KnowledgeArticle> findBySlugAndPublishedTrue(String slug);
  boolean existsBySlugIgnoreCase(String slug);
  List<KnowledgeArticle> findByPublishedTrueOrderByUpdatedAtDesc();
}
