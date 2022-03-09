package com.ssafy.togetherhomt.exercise.todayExercise;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TodayExerciseDto {

    private String exercise;

    private Boolean done;
}
