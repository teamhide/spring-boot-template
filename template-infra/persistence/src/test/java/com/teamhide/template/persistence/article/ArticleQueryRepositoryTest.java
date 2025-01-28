package com.teamhide.template.persistence.article;

import com.teamhide.template.persistence.test.RepositoryTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;

@RepositoryTest
@Import({ArticleQueryRepository.class})
class ArticleQueryRepositoryTest {
    @Autowired private ArticleQueryRepository articleQueryRepository;
    @Autowired private ArticleJpaRepository articleJpaRepository;
    @Autowired private CommentJpaRepository commentJpaRepository;
}
