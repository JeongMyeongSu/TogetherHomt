package com.ssafy.togetherhomt.exercise.record;

import com.ssafy.togetherhomt.common.CommonService;
import com.ssafy.togetherhomt.exercise.attendance.AttendanceRepository;
import com.ssafy.togetherhomt.exercise.todayExercise.TodayExerciseRepository;
import com.ssafy.togetherhomt.exercise.exercise.*;
import com.ssafy.togetherhomt.user.User;
import com.ssafy.togetherhomt.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Service
public class RecordService {

    private UserRepository userRepository;
    private RecordRepository recordRepository;
    private AttendanceRepository attendanceRepository;
    private TodayExerciseRepository todayExerciseRepository;
    private ExerciseRepository exerciseRepository;
    private CommonService commonService;

    @Autowired
    public RecordService(UserRepository userRepository, RecordRepository recordRepository, AttendanceRepository attendanceRepository, TodayExerciseRepository todayExerciseRepository, ExerciseRepository exerciseRepository, CommonService commonService) {
        this.userRepository = userRepository;
        this.recordRepository = recordRepository;
        this.attendanceRepository = attendanceRepository;
        this.todayExerciseRepository = todayExerciseRepository;
        this.exerciseRepository = exerciseRepository;
        this.commonService = commonService;
    }

    // 내 운동 기록 남기기
    public String create(RecordDto recordDto){

        User userTemp = commonService.getLoginUser();
        User user = userRepository.findByEmail(userTemp.getEmail());
        Exercise exercise = exerciseRepository.findByName(recordDto.getExercise());
        String record_time = new SimpleDateFormat("yyyyMMdd").format(Calendar.getInstance().getTime());

        List<Record> records = recordRepository.findRecordsByUserAndDate(user, record_time);
        for(Record r:records){
            if(r.getExercise().getName()==exercise.getName()) return "이미 기록된 운동";
        }

        Record record = Record.builder()
                .user(user)
                .countCheck(recordDto.getCountCheck())
                .exercise(exercise)
                .date(record_time)
                .build();
        recordRepository.save(record);
        return "success";
    }

    // 개인 운동 기록 달력 조회
    // 날짜와 운동이름만 반환
    public List<RecordDto> personalRecord(String email){
        User user = userRepository.findByEmail(email);
        List<Record> records = recordRepository.findAllByUser(user);
        List<RecordDto> personalRecords = new ArrayList<>();
        for(int i=0;i<records.size();i++){
            RecordDto recordDto = RecordDto.builder()
                    .date(records.get(i).getDate())
                    .exercise(records.get(i).getExercise().getName())
                    .build();
            personalRecords.add(recordDto);
        }
        return personalRecords;
    }

    // 개인 운동 날짜 상세
    // ( "abc33@google.com" , "20220210" ) 형태로 들어옴
    public List<RecordDto> personalDayRecord(String email, String date){
        User user = userRepository.findByEmail(email);
        List<Record> records = recordRepository.findRecordsByUserAndDate(user, date);
        List<RecordDto> personalRecords = new ArrayList<>();
        for(int i=0;i< records.size();i++){
            RecordDto recordDto = RecordDto.builder()
                    .date(records.get(i).getDate())
                    .countCheck(records.get(i).getCountCheck())
                    .exercise(records.get(i).getExercise().getName())
                    .build();
            personalRecords.add(recordDto);
        }
        return personalRecords;
    }




}
