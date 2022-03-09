package com.ssafy.togetherhomt.user.follow;

import com.ssafy.togetherhomt.user.User;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "follow")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class Follow {

    @Id
    @Column(name = "follow_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long followId;

    // 팔로우를 하는 사람
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "follower")
    @ToString.Exclude
    private User follower;

    // 팔로우를 하는 대상
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "following")
    @ToString.Exclude
    private User following;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Follow follow = (Follow) o;
        return followId != null && Objects.equals(followId, follow.followId);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

}
