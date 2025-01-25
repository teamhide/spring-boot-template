package com.teamhide.template.api.article.controller.dto;

import com.teamhide.template.domain.article.usecase.dto.CreateArticleRequestDto;
import jakarta.validation.constraints.NotNull;

public class CreateArticleRequest {
    @NotNull private String title;

    @NotNull private String content;

    public CreateArticleRequestDto toRequestDto() {
        return CreateArticleRequestDto.builder().title(title).content(content).build();
    }
}
