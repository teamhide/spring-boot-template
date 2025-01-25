package com.teamhide.template.domain.article.usecase.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ArticleDto {
    private Long id;
    private String title;
    private String content;
}
