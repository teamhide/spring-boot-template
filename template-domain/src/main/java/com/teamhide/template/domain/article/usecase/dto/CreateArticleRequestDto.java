package com.teamhide.template.domain.article.usecase.dto;

import lombok.Builder;

@Builder
public record CreateArticleRequestDto(String title, String content) {}
