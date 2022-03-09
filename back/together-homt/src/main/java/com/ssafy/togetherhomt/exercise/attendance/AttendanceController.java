package com.ssafy.togetherhomt.exercise.attendance;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/exercise/attendance")
public class AttendanceController {

    private AttendanceService attendanceService;

    @Autowired
    public AttendanceController(AttendanceService attendanceService) {
        this.attendanceService = attendanceService;
    }

    // 오늘 운동 안 한 사람들
    @GetMapping("/absentees")
    public ResponseEntity<List<AttendanceDto>> todayAttendance(){
        List<AttendanceDto> result = attendanceService.todayAttendance();
        return new ResponseEntity(result, HttpStatus.OK);
    }

    @GetMapping("/followings/absentees")
    public ResponseEntity<List<AttendanceDto>> todayFollowersAttendance(){
        List<AttendanceDto> result = attendanceService.todayFollowingsAttendance();
        return new ResponseEntity(result, HttpStatus.OK);
    }
}
