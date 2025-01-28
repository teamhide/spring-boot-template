package com.teamhide.template.domain.article;

import com.teamhide.template.domain.config.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
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

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "comment_id")
    private List<Comment> comments;

    @Builder
    public Article(final Long id, final String title, final String content) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.comments = new ArrayList<>();
    }

    public void changeTitle(final String title) {
        this.title = title;
    }

    public void changeContent(final String content) {
        this.content = content;
    }

    public void addComment(final Comment comment) {
        this.comments.add(comment);
    }

    public void removeComment(final Comment comment) {
        final boolean isExist = this.comments.stream().anyMatch(c -> c.getId().equals(comment.getId()));
        if (!isExist) {
            throw new IllegalArgumentException("Comment does not exist");
        }
        this.comments.remove(comment);
    }
}
