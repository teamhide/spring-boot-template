package com.teamhide.template.api.article.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.teamhide.template.api.article.controller.dto.CreateArticleRequest;
import com.teamhide.template.support.IntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

class ArticleControllerTest extends IntegrationTest {
    private static final String BASE_URL = "/v1/article";

    @Test
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
}
