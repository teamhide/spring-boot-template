package com.teamhide.template.persistence.article;

import com.teamhide.template.domain.article.Article;
import com.teamhide.template.domain.article.Comment;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentJpaRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllByArticle(Article article);
}
