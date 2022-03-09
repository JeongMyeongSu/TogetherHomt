package com.ssafy.togetherhomt.exercise.attendance;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AttendanceDto {

    private String username;

    private String email;

    private String image;

    private Boolean done;
}
