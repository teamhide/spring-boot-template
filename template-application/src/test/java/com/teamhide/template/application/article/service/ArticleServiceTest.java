package com.teamhide.template.application.article.service;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.teamhide.template.UnitTest;
import com.teamhide.template.domain.article.Article;
import com.teamhide.template.domain.article.ArticleRepository;
import com.teamhide.template.domain.article.dto.ArticleDto;
import com.teamhide.template.domain.article.dto.CreateArticleRequestDto;
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
}
