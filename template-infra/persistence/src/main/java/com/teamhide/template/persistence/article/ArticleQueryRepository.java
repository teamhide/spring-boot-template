package com.teamhide.template.persistence.article;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ArticleQueryRepository {
    private final JPAQueryFactory queryFactory;
}
