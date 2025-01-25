package com.teamhide.template.domain.article.repository;

import com.teamhide.template.domain.article.entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleJpaRepository extends JpaRepository<Article, Long> {}
