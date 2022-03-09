package com.ssafy.togetherhomt.feed.like;

import com.ssafy.togetherhomt.feed.Feed;
import com.ssafy.togetherhomt.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LikeRepository extends JpaRepository<Like, Long> {

    Like findByUserAndFeed(User user, Feed feed);
    List<Like> findAllByFeed(Feed feed);
}
