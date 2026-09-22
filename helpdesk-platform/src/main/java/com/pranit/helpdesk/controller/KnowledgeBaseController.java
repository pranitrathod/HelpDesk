package com.pranit.helpdesk.controller;

import com.pranit.helpdesk.dto.BusinessModuleDtos.ArticleResponse;
import com.pranit.helpdesk.dto.BusinessModuleDtos.CreateArticleRequest;
import com.pranit.helpdesk.service.KnowledgeBaseService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/** Provides self-service articles that can deflect repeat support requests. */
@RestController
@RequestMapping("/api/v1/knowledge-base")
@RequiredArgsConstructor
public class KnowledgeBaseController {

  private final KnowledgeBaseService knowledgeBaseService;

  @PostMapping("/articles")
  public ResponseEntity<ArticleResponse> create(
      @Valid @RequestBody CreateArticleRequest request) {
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(knowledgeBaseService.publish(request));
  }

  @GetMapping("/articles")
  public List<ArticleResponse> listPublished() {
    return knowledgeBaseService.listPublished();
  }

  @GetMapping("/articles/{slug}")
  public ArticleResponse get(@PathVariable String slug) {
    return knowledgeBaseService.getPublished(slug);
  }

  @PostMapping("/articles/{slug}/helpful")
  public ArticleResponse markHelpful(@PathVariable String slug) {
    return knowledgeBaseService.markHelpful(slug);
  }
}
