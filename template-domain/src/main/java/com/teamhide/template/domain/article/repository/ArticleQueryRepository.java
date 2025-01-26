package com.teamhide.template.domain.article.repository;

import static com.teamhide.template.domain.article.entity.QArticle.article;
import static com.teamhide.template.domain.article.entity.QComment.comment;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.teamhide.template.domain.article.repository.dto.ArticleDetail;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ArticleQueryRepository {
    private final JPAQueryFactory queryFactory;

    public ArticleDetail findDetailById(final Long articleId) {
        return queryFactory
                .select(
                        Projections.constructor(
                                ArticleDetail.class,
                                article.id,
                                article.title,
                                article.content,
                                Projections.list(
                                        Projections.constructor(
                                                ArticleDetail.Comment.class,
                                                comment.id,
                                                comment.content,
                                                comment.createdAt,
                                                comment.updatedAt)),
                                article.createdAt,
                                article.updatedAt))
                .from(article)
                .leftJoin(article.comments, comment)
                .where(article.id.eq(articleId))
                .fetchOne();
    }
}
