package com.ssafy.togetherhomt.common;

import com.ssafy.togetherhomt.config.auth.PrincipalDetails;
import com.ssafy.togetherhomt.feed.Feed;
import com.ssafy.togetherhomt.feed.like.LikeRepository;
import com.ssafy.togetherhomt.feed.tag.Tag;
import com.ssafy.togetherhomt.notification.Notification;
import com.ssafy.togetherhomt.notification.NotificationDto;
import com.ssafy.togetherhomt.user.User;
import com.ssafy.togetherhomt.user.UserDto;
import com.ssafy.togetherhomt.user.UserRepository;
import com.ssafy.togetherhomt.user.follow.FollowRepository;
import com.ssafy.togetherhomt.user.group.FeedDto;
import com.ssafy.togetherhomt.user.group.Group;
import com.ssafy.togetherhomt.user.group.GroupDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommonService {

    /*** Repository ***/
    private final UserRepository userRepository;
    private final FollowRepository followRepository;
    private final LikeRepository likeRepository;


    public User getLoginUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof PrincipalDetails)
            return ((PrincipalDetails)principal).getUser();
        else
            return new User();
    }


    // --------------------------------------------------

    /** User Entity --> DTO builder **/
    public UserDto builder(User user, boolean verbose) {
        UserDto.UserDtoBuilder userDtoBuilder = UserDto.builder();
        userDtoBuilder
                .email(user.getEmail())
                .username(user.getUsername())
                .role(user.getRole())
                .imagePath(user.getImagePath())
                .introduce(user.getIntroduce());

        if (user.getGroup() != null)
            userDtoBuilder.group(this.builder(user.getGroup()));

        User loginUser = this.getLoginUser();
        if (followRepository.findByFollowerAndFollowing(loginUser, user) != null || user.getEmail().equals(loginUser.getEmail()))
            userDtoBuilder.followed(true);

        if (verbose) {
            userDtoBuilder
                    .cntFollower(followRepository.countByFollowing(user))
                    .cntFollowing(followRepository.countByFollower(user));
        }

        return userDtoBuilder.build();
    }

    /** Group Entity --> DTO builder **/
    public GroupDto builder(Group group) {
        GroupDto.GroupDtoBuilder groupDtoBuilder = GroupDto.builder();
        groupDtoBuilder
                .groupId(group.getGroupId())
                .groupName(group.getName());

        return groupDtoBuilder.build();
    }

    /** Feed Entity --> DTO builder **/
    public FeedDto builder(Feed feed) {
        FeedDto.FeedDtoBuilder feedDtoBuilder = FeedDto.builder();
        feedDtoBuilder
                .feedId(feed.getFeedId())
                .writer(this.builder(feed.getUser(), false))
                .title(feed.getTitle())
                .content(feed.getContent())
                .mediaUrl(feed.getMediaUrl())
                .createdAt(feed.getCreatedAt());

        feedDtoBuilder
                .likeCnt(feed.getLikeCnt())
                .tags(feed.getTags().stream()
                        .map(Tag::getName)
                        .collect(Collectors.toList()));

        feedDtoBuilder.likeStatus(
                likeRepository.findByUserAndFeed(userRepository.findByEmail(this.getLoginUser().getEmail()), feed) != null
        );

        return feedDtoBuilder.build();
    }

    /** Notification Entity --> DTO builder **/
    public NotificationDto builder(Notification notification) {
        return NotificationDto.builder()
                .notificationId(notification.getNotificationId())
                .sender(this.builder(notification.getSender(), false))
                .notificationType(notification.getNotificationType())
                .sentDate(notification.getSentDate())
                .build();
    }

}
