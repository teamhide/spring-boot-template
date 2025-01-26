package com.teamhide.template.domain.article.dto;

import lombok.Builder;

@Builder
public record CreateArticleRequestDto(String title, String content) {}
