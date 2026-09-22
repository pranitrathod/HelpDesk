package com.pranit.helpdesk.service.impl;

import com.pranit.helpdesk.domain.KnowledgeArticle;
import com.pranit.helpdesk.dto.BusinessModuleDtos.ArticleResponse;
import com.pranit.helpdesk.dto.BusinessModuleDtos.CreateArticleRequest;
import com.pranit.helpdesk.exception.ConflictException;
import com.pranit.helpdesk.exception.ResourceNotFoundException;
import com.pranit.helpdesk.repository.KnowledgeArticleRepository;
import com.pranit.helpdesk.service.KnowledgeBaseService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class KnowledgeBaseServiceImpl implements KnowledgeBaseService {

  private final KnowledgeArticleRepository repository;

  @Override
  public ArticleResponse publish(CreateArticleRequest request) {
    if (repository.existsBySlugIgnoreCase(request.getSlug())) {
      throw new ConflictException("An article already uses slug " + request.getSlug());
    }

    return response(repository.save(
        new KnowledgeArticle(
            request.getSlug(),
            request.getTitle(),
            request.getSummary(),
            request.getContent(),
            request.isPublished())));
  }

  @Override
  @Transactional(readOnly = true)
  public List<ArticleResponse> listPublished() {
    return repository.findByPublishedTrueOrderByUpdatedAtDesc().stream()
        .map(this::response)
        .toList();
  }

  @Override
  @Transactional(readOnly = true)
  public ArticleResponse getPublished(String slug) {
    return response(article(slug));
  }

  @Override
  public ArticleResponse markHelpful(String slug) {
    KnowledgeArticle article = article(slug);
    article.voteHelpful();
    return response(article);
  }

  private KnowledgeArticle article(String slug) {
    return repository.findBySlugAndPublishedTrue(slug)
        .orElseThrow(() -> new ResourceNotFoundException("Published article", slug));
  }

  private ArticleResponse response(KnowledgeArticle article) {
    return new ArticleResponse(
        article.getId(),
        article.getSlug(),
        article.getTitle(),
        article.getSummary(),
        article.getContent(),
        article.isPublished(),
        article.getHelpfulVotes(),
        article.getUpdatedAt());
  }
}
