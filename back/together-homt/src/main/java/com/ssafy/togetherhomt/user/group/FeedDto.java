package com.ssafy.togetherhomt.user.group;

import com.ssafy.togetherhomt.user.UserDto;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter @Setter
@Builder
public class FeedDto {

    private Long feedId;

    private UserDto writer;
    private String title;
    private String content;
    private String mediaUrl;

    private LocalDateTime createdAt;


    private Long likeCnt;

    private List<String> tags;

    private boolean likeStatus;

}
