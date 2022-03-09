package com.ssafy.togetherhomt.exercise.attendance;

import com.ssafy.togetherhomt.common.CommonService;
import com.ssafy.togetherhomt.user.User;
import com.ssafy.togetherhomt.user.UserRepository;
import com.ssafy.togetherhomt.user.follow.Follow;
import com.ssafy.togetherhomt.user.follow.FollowRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class AttendanceService {

    private AttendanceRepository attendanceRepository;
    private UserRepository userRepository;
    private CommonService commonService;
    private FollowRepository followRepository;

    @Autowired
    public AttendanceService(AttendanceRepository attendanceRepository, UserRepository userRepository, CommonService commonService, FollowRepository followRepository) {
        this.attendanceRepository = attendanceRepository;
        this.userRepository = userRepository;
        this.commonService = commonService;
        this.followRepository = followRepository;
    }

    // 00시가 되면 초기화
    @Scheduled(cron = "0 0 0 * * ?")
    public void create(){
        System.out.println("attendance created!!!!!!!");
        List<User> users = userRepository.findAll();
        attendanceRepository.deleteAll();

        for(User user:users){
            Attendance attendance = Attendance.builder()
                    .user(user)
                    .done(false)
                    .build();
            attendanceRepository.save(attendance);
        }
    }

    // 오늘 불참러 조회
    public List<AttendanceDto> todayAttendance(){
        List<AttendanceDto> attendees = new ArrayList<>();
        for(Attendance attendance:attendanceRepository.findAllByDoneFalse()){
            AttendanceDto attendanceDto = AttendanceDto.builder()
                    .username(attendance.getUser().getUsername())
                    .image(attendance.getUser().getImagePath())
                    .done(attendance.getDone())
                    .build();
            attendees.add(attendanceDto);
        }
        return attendees;
    }

    // 팔로우 한 사람 중 불참러
    public List<AttendanceDto> todayFollowingsAttendance(){
        User userTemp = commonService.getLoginUser();
        User user = userRepository.findByEmail(userTemp.getEmail());
        List<Follow> follows = followRepository.findByFollower(user);

        List<AttendanceDto> attendees = new ArrayList<>();
        for(Follow follow:follows){
            Attendance followAttendance = attendanceRepository.findByUser(follow.getFollowing());
            AttendanceDto attendanceDto = AttendanceDto.builder()
                    .username(follow.getFollowing().getUsername())
                    .email(follow.getFollowing().getEmail())
                    .image(follow.getFollowing().getImagePath())
                    .done(followAttendance.getDone())
                    .build();
            attendees.add(attendanceDto);
        }
        Collections.sort(attendees,(a,b)->{
            if(a.getDone()) return 1;
            else return -1;
        });
        return attendees;
    }

}
