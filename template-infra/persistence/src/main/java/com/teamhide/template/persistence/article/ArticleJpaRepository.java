package com.teamhide.template.persistence.article;

import com.teamhide.template.domain.article.Article;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleJpaRepository extends JpaRepository<Article, Long> {}
