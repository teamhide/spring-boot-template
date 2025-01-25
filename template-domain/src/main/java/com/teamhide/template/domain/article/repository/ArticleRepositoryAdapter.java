package com.teamhide.template.domain.article.repository;

import com.teamhide.template.domain.article.entity.Article;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ArticleRepositoryAdapter implements ArticleRepository {
    private final ArticleJpaRepository articleJpaRepository;

    @Override
    public Article save(final Article article) {
        return articleJpaRepository.save(article);
    }
}
