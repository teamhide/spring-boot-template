package com.teamhide.template.application.article.service;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.teamhide.template.UnitTest;
import com.teamhide.template.domain.article.Article;
import com.teamhide.template.domain.article.ArticleRepository;
import com.teamhide.template.domain.article.dto.ArticleDetail;
import com.teamhide.template.domain.article.dto.ArticleDto;
import com.teamhide.template.domain.article.dto.CreateArticleRequestDto;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

@UnitTest
class ArticleServiceTest {
    @Mock private ArticleRepository articleRepository;

    @InjectMocks private ArticleService articleService;

    @Test
    @DisplayName("글을 생성한다")
    void testCreateArticle() {
        // Given
        final CreateArticleRequestDto requestDto =
                CreateArticleRequestDto.builder().title("title").content("content").build();
        final Article article = Article.builder().id(1L).title("title").content("content").build();
        when(articleRepository.save(any())).thenReturn(article);

        // When
        final ArticleDto sut = articleService.createArticle(requestDto);

        // Then
        assertThat(sut.getId()).isEqualTo(1L);
        assertThat(sut.getTitle()).isEqualTo("title");
        assertThat(sut.getContent()).isEqualTo("content");
    }

    @Test
    @DisplayName("글을 조회한다")
    void testGetArticle() {
        // Given
        final Long articleId = 1L;
        final LocalDateTime now = LocalDateTime.now();
        final ArticleDetail articleDetail =
                ArticleDetail.builder()
                        .id(1L)
                        .title("title")
                        .content("content")
                        .comments(Collections.emptyList())
                        .createdAt(now)
                        .updatedAt(now)
                        .build();
        when(articleRepository.findById(any())).thenReturn(Optional.of(articleDetail));

        // When
        final ArticleDetail article = articleService.getArticle(articleId);

        // Then
        assertThat(article.getId()).isEqualTo(articleId);
    }
}
