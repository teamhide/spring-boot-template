package com.teamhide.template.domain.article;

import com.teamhide.template.domain.config.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "comment")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Comment extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "article_id", nullable = false)
    private Article article;

    private String content;

    @Builder
    public Comment(final Long id, final Article article, final String content) {
        this.id = id;
        this.article = article;
        this.content = content;
    }

    public void setArticle(final Article article) {
        this.article = article;
    }
}
