package com.ssafy.togetherhomt.user;

import com.ssafy.togetherhomt.user.group.GroupDto;
import io.swagger.annotations.ApiParam;
import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class UserDto {

    @ApiParam(value = "사용자 계정 주소")
    @NotNull
    @Size(max = 50)
    private String email;
    @ApiParam(value = "사용자 계정 별명(nickname)")
    @NotNull
    @Size(min = 3, max = 10)
    private String username;
    @ApiParam(value = "사용자 계정 권한")
    private String role;

    @ApiParam(value = "사용자 계정 소개")
    private String introduce;

    @ApiParam(value = "사용자 계정 프로필 사진 경로")
    private String imagePath;

    @ApiParam(value = "사용자 계정을 팔로우하는 사람 수")
    private Long cntFollower;
    @ApiParam(value = "사용자 계정이 팔로우하는 사람 수")
    private Long cntFollowing;

    @ApiParam(value = "로그인 유저가 팔로우 했는지 여부")
    private Boolean followed;

    private GroupDto group;

}
