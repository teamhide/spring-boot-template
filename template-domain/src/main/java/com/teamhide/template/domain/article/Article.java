package com.teamhide.template.domain.article;

import com.teamhide.template.domain.config.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "article")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Article extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "content")
    private String content;

    @Builder
    public Article(final Long id, final String title, final String content) {
        this.id = id;
        this.title = title;
        this.content = content;
    }

    public void changeTitle(final String title) {
        this.title = title;
    }

    public void changeContent(final String content) {
        this.content = content;
    }
}
