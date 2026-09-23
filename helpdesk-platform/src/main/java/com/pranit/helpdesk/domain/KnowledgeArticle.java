package com.pranit.helpdesk.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.Instant;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "knowledge_articles")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class KnowledgeArticle {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, unique = true, length = 160)
  private String slug;

  @Column(nullable = false, length = 200)
  private String title;

  @Column(nullable = false, length = 500)
  private String summary;

  @Column(nullable = false, length = 12000)
  private String content;

  @Column(nullable = false)
  private boolean published;

  @Column(nullable = false)
  private int helpfulVotes;

  @Column(nullable = false, updatable = false)
  private Instant createdAt = Instant.now();

  @Column(nullable = false)
  private Instant updatedAt = Instant.now();

  public KnowledgeArticle(
      String slug, String title, String summary, String content, boolean published) {
    this.slug = slug;
    this.title = title;
    this.summary = summary;
    this.content = content;
    this.published = published;
  }

  public void voteHelpful() {
    helpfulVotes++;
    updatedAt = Instant.now();
  }
}
