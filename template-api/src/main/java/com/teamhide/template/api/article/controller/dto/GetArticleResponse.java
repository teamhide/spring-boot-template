package com.teamhide.template.api.article.controller.dto;

import com.teamhide.template.domain.article.dto.ArticleDetail;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class GetArticleResponse {
    private Long id;
    private String title;
    private String content;
    private List<Comment> comments;

    @Builder
    @Getter
    public static class Comment {
        private Long id;
        private String content;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
    }

    public static GetArticleResponse from(final ArticleDetail article) {
        return GetArticleResponse.builder()
                .id(article.getId())
                .title(article.getTitle())
                .content(article.getContent())
                .comments(
                        article.getComments().stream()
                                .map(
                                        comment ->
                                                Comment.builder()
                                                        .id(comment.getId())
                                                        .content(comment.getContent())
                                                        .createdAt(comment.getCreatedAt())
                                                        .updatedAt(comment.getUpdatedAt())
                                                        .build())
                                .toList())
                .build();
    }
}
