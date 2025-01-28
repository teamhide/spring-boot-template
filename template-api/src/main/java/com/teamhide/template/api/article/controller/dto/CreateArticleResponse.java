package com.teamhide.template.api.article.controller.dto;

import com.teamhide.template.domain.article.dto.ArticleDto;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class CreateArticleResponse {
    private Long id;
    private String title;
    private String content;

    public static CreateArticleResponse from(final ArticleDto article) {
        return CreateArticleResponse.builder()
                .id(article.getId())
                .title(article.getTitle())
                .content(article.getContent())
                .build();
    }
}
