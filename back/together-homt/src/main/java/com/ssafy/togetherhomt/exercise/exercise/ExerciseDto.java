package com.ssafy.togetherhomt.exercise.exercise;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ExerciseDto {

    private String name;

    private String part;

    private String type;

    private Long exerciseTime;

    private Long exerciseCount;

    private String videoUrl;
}
