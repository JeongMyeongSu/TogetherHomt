package com.ssafy.togetherhomt.feed;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ssafy.togetherhomt.baseEntity.BaseTimeEntity;
import com.ssafy.togetherhomt.feed.comment.Comment;
import com.ssafy.togetherhomt.feed.like.Like;
import com.ssafy.togetherhomt.feed.tag.Tag;
import com.ssafy.togetherhomt.user.User;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "feed")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class Feed extends BaseTimeEntity {

    @Id
    @Column(name = "feed_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long feedId;

    @Column(name = "title")
    private String title;

    @Column(name = "content")
    private String content;

    @Column(name = "media_url")
    private String mediaUrl;

    @Column(name = "like_cnt")
    private Long likeCnt;

    @Builder.Default
    @ManyToMany
    @JoinTable(name = "feed_tag",
            joinColumns = @JoinColumn(name = "feed_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id"))
    private List<Tag> tags = new ArrayList<Tag>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Builder.Default
    @OneToMany(mappedBy = "feed",cascade = CascadeType.ALL)
    @JsonIgnoreProperties({"feed"})
    private List<Comment> comments = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "feed",cascade = CascadeType.ALL)
    @JsonIgnoreProperties({"feed"})
    private List<Like> likes = new ArrayList<>();
}
