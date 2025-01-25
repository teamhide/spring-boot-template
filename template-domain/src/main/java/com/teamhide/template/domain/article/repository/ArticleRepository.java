package com.teamhide.template.domain.article.repository;

import com.teamhide.template.domain.article.entity.Article;

interface ArticleRepository {
    Article save(Article article);
}
