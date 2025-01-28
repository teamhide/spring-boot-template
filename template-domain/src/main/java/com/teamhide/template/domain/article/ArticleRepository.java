package com.teamhide.template.domain.article;

import com.teamhide.template.domain.article.dto.ArticleDetail;
import java.util.Optional;

public interface ArticleRepository {
    Article save(Article article);

    Optional<ArticleDetail> findById(Long articleId);
}
