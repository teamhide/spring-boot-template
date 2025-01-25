package com.teamhide.template.api.article.controller;

import com.teamhide.template.api.article.controller.dto.CreateArticleRequest;
import com.teamhide.template.api.article.controller.dto.CreateArticleResponse;
import com.teamhide.template.api.common.response.ApiResponse;
import com.teamhide.template.domain.article.usecase.ArticleUseCase;
import com.teamhide.template.domain.article.usecase.dto.ArticleDto;
import com.teamhide.template.domain.article.usecase.dto.CreateArticleRequestDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1")
@RequiredArgsConstructor
public class ArticleController {
    private final ArticleUseCase articleUseCase;

    @PostMapping("")
    public ApiResponse<CreateArticleResponse> createArticle(
            @RequestBody @Valid CreateArticleRequest request) {
        final CreateArticleRequestDto requestDto = request.toRequestDto();
        final ArticleDto article = articleUseCase.createArticle(requestDto);
        return ApiResponse.success(CreateArticleResponse.from(article), HttpStatus.OK);
    }
}
