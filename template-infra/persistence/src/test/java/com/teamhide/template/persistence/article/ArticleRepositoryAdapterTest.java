package com.teamhide.template.persistence.article;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.teamhide.template.UnitTest;
import com.teamhide.template.domain.article.Article;
import com.teamhide.template.domain.article.Comment;
import com.teamhide.template.domain.article.dto.ArticleDetail;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

@UnitTest
class ArticleRepositoryAdapterTest {
    @Mock private ArticleJpaRepository articleJpaRepository;
    @Mock private ArticleQueryRepository articleQueryRepository;
    @Mock private CommentJpaRepository commentJpaRepository;
    @InjectMocks private ArticleRepositoryAdapter articleRepositoryAdapter;

    @Test
    @DisplayName("글을 저장한다")
    void testSave() {
        // Given
        final Long articleId = 1L;
        final Article article =
                Article.builder().id(articleId).title("title").content("content").build();
        when(articleJpaRepository.save(any())).thenReturn(article);

        // When
        final Article sut = articleJpaRepository.save(article);

        // Then
        assertThat(sut.getId()).isEqualTo(articleId);
        verify(articleJpaRepository).save(any());
    }

    @Test
    @DisplayName("글을 댓글과 함께 조회한다")
    void testFindById() {
        // Given
        final Long articleId = 1L;
        final Article article =
                Article.builder().id(articleId).title("title").content("content").build();
        final Comment comment1 = Comment.builder().id(1L).content("content1").article(article).build();
        final Comment comment2 = Comment.builder().id(2L).content("content2").article(article).build();
        when(articleJpaRepository.findById(articleId)).thenReturn(Optional.of(article));
        when(commentJpaRepository.findAllByArticle(any())).thenReturn(List.of(comment1, comment2));

        // When
        final Optional<ArticleDetail> sut = articleRepositoryAdapter.findById(articleId);

        // Then
        assertThat(sut).isNotNull();
        final ArticleDetail articleDetail = sut.get();
        assertThat(articleDetail.getId()).isEqualTo(article.getId());
        assertThat(articleDetail.getTitle()).isEqualTo(article.getTitle());
        assertThat(articleDetail.getContent()).isEqualTo(article.getContent());
        assertThat(articleDetail.getComments().size()).isEqualTo(2);
        assertThat(articleDetail.getComments().get(0).getId()).isEqualTo(comment1.getId());
        assertThat(articleDetail.getComments().get(1).getId()).isEqualTo(comment2.getId());
    }
}
