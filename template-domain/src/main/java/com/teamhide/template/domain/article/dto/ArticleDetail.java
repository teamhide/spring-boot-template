package com.teamhide.template.domain.article.dto;

import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ArticleDetail {
    private Long id;
    private String title;
    private String content;
    private List<Comment> comments;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @Getter
    @Builder
    public static class Comment {
        private Long id;
        private String content;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
    }
}
