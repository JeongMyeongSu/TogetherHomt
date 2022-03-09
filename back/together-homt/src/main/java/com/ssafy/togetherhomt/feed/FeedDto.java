package com.ssafy.togetherhomt.feed;

import io.swagger.annotations.ApiParam;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FeedDto {

    private Long feedId;

    @ApiParam(value = "피드 제목")
    @NotNull
    private String title;

    @ApiParam(value = "피드 내용")
    @NotNull
    private String content;

    @ApiParam(value = "태그")
    private List<String> tags;

    @ApiParam(value = "게시 날짜시간")
    private LocalDateTime createdAt;

    @ApiParam(value = "수정 날짜시간")
    private LocalDateTime updatedAt;

    @ApiParam(value = "피드 첨부파일")
    private MultipartFile media;

    @ApiParam(value = "피드 첨부파일 저장 경로")
    private String mediaUrl;

    @ApiParam(value = "피드 좋아요 수")
    private Long likeCnt;

    @ApiParam(value = "피드 작성자")
    private String username;

    @ApiParam(value = "이메일")
    private String email;

    @ApiParam(value = "좋아요 여부")
    private Boolean likeStatus;
}
