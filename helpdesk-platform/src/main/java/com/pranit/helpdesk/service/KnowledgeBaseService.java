package com.pranit.helpdesk.service;
import com.pranit.helpdesk.dto.BusinessModuleDtos.ArticleResponse;
import com.pranit.helpdesk.dto.BusinessModuleDtos.CreateArticleRequest;
import java.util.List;
public interface KnowledgeBaseService {
  ArticleResponse publish(CreateArticleRequest request);
  List<ArticleResponse> listPublished();
  ArticleResponse getPublished(String slug);
  ArticleResponse markHelpful(String slug);
}
