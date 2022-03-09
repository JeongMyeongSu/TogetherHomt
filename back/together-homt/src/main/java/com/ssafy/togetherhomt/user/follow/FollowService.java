package com.ssafy.togetherhomt.user.follow;

import com.ssafy.togetherhomt.common.CommonService;
import com.ssafy.togetherhomt.user.User;
import com.ssafy.togetherhomt.user.UserDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class FollowService {

    private FollowRepository followRepository;

    private CommonService commonService;


    // 나를 팔로우 하는 사람 검색
    public List<UserDto> listFollowers(User user) {
        List<Follow> followers = followRepository.findByFollowing(user);

        List<UserDto> followerList = new ArrayList<>();
        for (Follow follow : followers) {
            User follower = follow.getFollower();
            followerList.add(
                    UserDto.builder()
                            .email(follower.getEmail())
                            .username(follower.getUsername())
                            .imagePath(follower.getImagePath())
                            .introduce(follower.getIntroduce())
                            .build()
            );
        }
        return followerList;
    }

    // 내가 팔로우 하는 사람 검색
    public List<UserDto> listFollowings(User user) {
        List<Follow> followings = followRepository.findByFollower(user);

        List<UserDto> followingList = new ArrayList<>();
        for (Follow follow : followings) {
            User following = follow.getFollowing();
            followingList.add(
                    UserDto.builder()
                            .email(following.getEmail())
                            .username(following.getUsername())
                            .imagePath(following.getImagePath())
                            .introduce(following.getIntroduce())
                            .build()
            );
        }
        return followingList;
    }

    // 나를 팔로우 하는 사람 및 내가 팔로우 하는 사람 검색
    public Map<String, List<UserDto>> listFollowersAndFollowings(User user) {
        Map<String, List<UserDto>> entireFollowInfo = new HashMap<>();
        entireFollowInfo.put("followers", listFollowers(user));
        entireFollowInfo.put("followings", listFollowings(user));
        return entireFollowInfo;
    }

    @Transactional
    public Follow follow(User you) {
        User me = commonService.getLoginUser();
        if (followRepository.findByFollowerAndFollowing(me, you) != null)
            return null;

        Follow follow = new Follow();
        follow.setFollower(me);
        follow.setFollowing(you);

        return followRepository.save(follow);
    }

    @Transactional
    public void unfollow(User you) {
        User me = commonService.getLoginUser();
        Follow follow = followRepository.findByFollowerAndFollowing(me, you);
        if (follow != null)
            followRepository.delete(follow);
    }

}

