package com.teamhide.template.domain.article.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ArticleDto {
    private Long id;
    private String title;
    private String content;
}
