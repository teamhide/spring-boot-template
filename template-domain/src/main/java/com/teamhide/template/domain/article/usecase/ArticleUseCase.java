package com.teamhide.template.domain.article.usecase;

import com.teamhide.template.domain.article.usecase.dto.ArticleDto;
import com.teamhide.template.domain.article.usecase.dto.CreateArticleRequestDto;

public interface ArticleUseCase {
    ArticleDto createArticle(CreateArticleRequestDto requestDto);
}
