package com.teamhide.template.api.article.controller.dto;

import com.teamhide.template.domain.article.dto.CreateArticleRequestDto;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class CreateArticleRequest {
    @NotNull private String title;

    @NotNull private String content;

    public CreateArticleRequest(final String title, final String content) {
        this.title = title;
        this.content = content;
    }

    public CreateArticleRequestDto toRequestDto() {
        return CreateArticleRequestDto.builder().title(title).content(content).build();
    }
}
