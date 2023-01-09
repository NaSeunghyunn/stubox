package com.nastudy.stubox.domain.entity;

import com.nastudy.stubox.domain.PostType;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;

import static javax.persistence.EnumType.STRING;
import static javax.persistence.FetchType.LAZY;
import static lombok.AccessLevel.PROTECTED;

@Getter
@NoArgsConstructor(access = PROTECTED)
@Entity
public class Post extends BaseEntity {
    @Id
    @GeneratedValue
    private Long id;

    @Enumerated(STRING)
    @Column(nullable = false)
    private PostType postType;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "writer_id")
    private Member writer;

    @ColumnDefault("0")
    @Column(nullable = false)
    private int viewCount;

    @ColumnDefault("0")
    @Column(nullable = false)
    private int commentCount;

    @ColumnDefault("0")
    @Column(nullable = false)
    private int likeCount;

    @Column(name = "preview_url")
    private String previewURL;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "card_id")
    private Card card;

    @Builder
    public Post(Long id, PostType postType, String title, String content, Member writer, int viewCount, String previewURL, Card card) {
        this.id = id;
        this.postType = postType;
        this.title = title;
        this.content = content;
        this.writer = writer;
        this.viewCount = viewCount;
        this.previewURL = previewURL;
        this.card = card;
    }

    public void viewCountUp() {
        viewCount++;
    }

    public void commentCountUp() {
        commentCount++;
    }

    public void likeCountUp() {
        likeCount++;
    }

    public void edit(String title, String content, String previewURL){
        this.title = title;
        this.content = content;
        this.previewURL = previewURL;
    }
}
