package com.pranit.helpdesk.controller;
import com.pranit.helpdesk.dto.BusinessModuleDtos.*;
import com.pranit.helpdesk.service.KnowledgeBaseService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
/** Provides self-service articles that can deflect repeat support requests. */
@RestController
@RequestMapping("/api/v1/knowledge-base")
public class KnowledgeBaseController {
  private final KnowledgeBaseService service;
  public KnowledgeBaseController(KnowledgeBaseService service) {
    this.service = service;
  }
  @PostMapping("/articles")
  public ResponseEntity<ArticleResponse> create(@Valid @RequestBody CreateArticleRequest request) {
    return ResponseEntity.status(HttpStatus.CREATED).body(service.publish(request));
  }
  @GetMapping("/articles")
  public List<ArticleResponse> listPublished() {
    return service.listPublished();
  }
  @GetMapping("/articles/{slug}")
  public ArticleResponse get(@PathVariable String slug) {
    return service.getPublished(slug);
  }
  @PostMapping("/articles/{slug}/helpful")
  public ArticleResponse markHelpful(@PathVariable String slug) {
    return service.markHelpful(slug);
  }
}
