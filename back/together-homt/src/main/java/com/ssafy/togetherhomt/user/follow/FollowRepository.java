package com.ssafy.togetherhomt.user.follow;

import com.ssafy.togetherhomt.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FollowRepository extends JpaRepository<Follow, Long> {

    // 나를 팔로우 하는 사람 검색
    List<Follow> findByFollowing(User following);
    // 나를 팔로우 하는 사람 수 검색
    Long countByFollowing(User user);

    // 내가 팔로우 하는 사람 검색
    List<Follow> findByFollower(User follower);
    // 내가 팔로우 하는 사람 수 검색
    Long countByFollower(User user);

    // 특정 팔로우 관계 검색
    Follow findByFollowerAndFollowing(User follower, User following);

}