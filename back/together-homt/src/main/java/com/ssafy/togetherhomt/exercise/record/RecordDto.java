package com.ssafy.togetherhomt.exercise.record;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RecordDto {

//    private String video_url;

    private Long countCheck;

    private String date;

    private String exercise;

}
