package com.teamhide.template.domain.article;

import com.teamhide.template.domain.article.dto.ArticleDetail;
import com.teamhide.template.domain.article.dto.ArticleDto;
import com.teamhide.template.domain.article.dto.CreateArticleRequestDto;

public interface ArticleUseCase {
    ArticleDto createArticle(CreateArticleRequestDto requestDto);

    ArticleDetail getArticle(Long id);
}
