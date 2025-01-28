package com.teamhide.template.application.article.service;

import com.teamhide.template.domain.article.Article;
import com.teamhide.template.domain.article.ArticleRepository;
import com.teamhide.template.domain.article.ArticleUseCase;
import com.teamhide.template.domain.article.dto.ArticleDto;
import com.teamhide.template.domain.article.dto.CreateArticleRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ArticleService implements ArticleUseCase {
    private final ArticleRepository articleRepository;

    @Transactional
    @Override
    public ArticleDto createArticle(final CreateArticleRequestDto requestDto) {
        final Article article =
                Article.builder().title(requestDto.title()).content(requestDto.content()).build();
        final Article savedArticle = articleRepository.save(article);
        return ArticleDto.builder()
                .id(savedArticle.getId())
                .title(savedArticle.getTitle())
                .content(savedArticle.getContent())
                .build();
    }
}
