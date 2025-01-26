package com.teamhide.template.domain.article.repository;

import com.teamhide.template.domain.test.RepositoryTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;

@RepositoryTest
@Import(ArticleQueryRepository.class)
class ArticleQueryRepositoryTest {
    @Autowired private ArticleQueryRepository articleQueryRepository;

    @Test
    @DisplayName("댓글과 함께 글을 조회한다")
    void testFindDetailById() {}
}
