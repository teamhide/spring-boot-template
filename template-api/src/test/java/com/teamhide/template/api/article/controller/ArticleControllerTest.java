package com.teamhide.template.api.article.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.teamhide.template.api.article.controller.dto.CreateArticleRequest;
import com.teamhide.template.domain.article.Article;
import com.teamhide.template.domain.article.Comment;
import com.teamhide.template.persistence.article.ArticleJpaRepository;
import com.teamhide.template.persistence.article.CommentJpaRepository;
import com.teamhide.template.support.IntegrationTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

class ArticleControllerTest extends IntegrationTest {
    private static final String BASE_URL = "/v1/article";

    @Autowired private ArticleJpaRepository articleJpaRepository;

    @Autowired private CommentJpaRepository commentJpaRepository;

    @Test
    @DisplayName("글을 생성한다")
    void testCreateArticle() throws Exception {
        // Given
        final String title = "title";
        final String content = "content";
        final CreateArticleRequest request = new CreateArticleRequest(title, content);

        // When, Then
        mockMvc
                .perform(
                        post(BASE_URL)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value(title))
                .andExpect(jsonPath("$.content").value(content));
    }

    @Test
    @DisplayName("글을 댓글과 함께 조회한다")
    void testGetArticle() throws Exception {
        // Given
        final Article article = Article.builder().title("title").content("content").build();
        articleJpaRepository.save(article);

        final Comment comment1 = Comment.builder().content("content1").article(article).build();
        final Comment comment2 = Comment.builder().content("content2").article(article).build();
        commentJpaRepository.save(comment1);
        commentJpaRepository.save(comment2);

        // When, Then
        mockMvc
                .perform(get(BASE_URL + "/" + article.getId()).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value(article.getTitle()))
                .andExpect(jsonPath("$.content").value(article.getContent()))
                .andExpect(jsonPath("$.comments[0].id").value(comment1.getId()))
                .andExpect(jsonPath("$.comments[0].content").value(comment1.getContent()))
                .andExpect(jsonPath("$.comments[1].id").value(comment2.getId()))
                .andExpect(jsonPath("$.comments[1].content").value(comment2.getContent()));
    }
}
