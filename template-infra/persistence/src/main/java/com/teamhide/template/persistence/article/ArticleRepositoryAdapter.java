package com.teamhide.template.persistence.article;

import com.teamhide.template.domain.article.Article;
import com.teamhide.template.domain.article.ArticleRepository;
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
