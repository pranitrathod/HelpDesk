package com.pranit.productsupport.contracts;
import java.time.Instant;
public record CommentResponse(Long commentId, Long ticketId, Long authorId, String authorRole, String content, Instant createdAt) {}
