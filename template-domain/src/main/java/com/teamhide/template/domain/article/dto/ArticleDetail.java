package com.teamhide.template.domain.article.dto;

import java.time.LocalDateTime;
import java.util.List;
import lombok.Getter;

@Getter
public class ArticleDetail {
    private Long id;
    private String title;
    private String content;
    private List<Comment> comments;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static class Comment {
        private Long id;
        private String content;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
    }
}
