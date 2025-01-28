package com.teamhide.template.persistence.article;

import com.teamhide.template.domain.article.Article;
import com.teamhide.template.domain.article.ArticleRepository;
import com.teamhide.template.domain.article.dto.ArticleDetail;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ArticleRepositoryAdapter implements ArticleRepository {
    private final ArticleJpaRepository articleJpaRepository;
    private final ArticleQueryRepository articleQueryRepository;
    private final CommentJpaRepository commentJpaRepository;

    @Override
    public Article save(final Article article) {
        return articleJpaRepository.save(article);
    }

    @Override
    public Optional<ArticleDetail> findById(final Long articleId) {
        return articleJpaRepository
                .findById(articleId)
                .map(
                        article -> {
                            final List<ArticleDetail.Comment> comments =
                                    commentJpaRepository.findAllByArticle(article).stream()
                                            .map(
                                                    comment ->
                                                            ArticleDetail.Comment.builder()
                                                                    .id(comment.getId())
                                                                    .content(comment.getContent())
                                                                    .createdAt(comment.getCreatedAt())
                                                                    .updatedAt(comment.getUpdatedAt())
                                                                    .build())
                                            .toList();

                            return ArticleDetail.builder()
                                    .id(article.getId())
                                    .title(article.getTitle())
                                    .content(article.getContent())
                                    .comments(comments)
                                    .createdAt(article.getCreatedAt())
                                    .updatedAt(article.getUpdatedAt())
                                    .build();
                        });
    }
}
