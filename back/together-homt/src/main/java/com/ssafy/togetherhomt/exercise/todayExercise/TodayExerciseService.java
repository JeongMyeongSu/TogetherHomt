package com.ssafy.togetherhomt.exercise.todayExercise;

import com.ssafy.togetherhomt.exercise.attendance.Attendance;
import com.ssafy.togetherhomt.common.CommonService;
import com.ssafy.togetherhomt.exercise.attendance.AttendanceRepository;
import com.ssafy.togetherhomt.exercise.exercise.Exercise;
import com.ssafy.togetherhomt.exercise.exercise.ExerciseRepository;
import com.ssafy.togetherhomt.exercise.record.RecordDto;
import com.ssafy.togetherhomt.exercise.record.RecordService;
import com.ssafy.togetherhomt.user.User;
import com.ssafy.togetherhomt.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class TodayExerciseService {

    private UserRepository userRepository;
    private TodayExerciseRepository todayExerciseRepository;
    private AttendanceRepository attendanceRepository;
    private ExerciseRepository exerciseRepository;
    private CommonService commonService;
    private RecordService recordService;

    @Autowired
    public TodayExerciseService(UserRepository userRepository, TodayExerciseRepository todayExerciseRepository, AttendanceRepository attendanceRepository, ExerciseRepository exerciseRepository, CommonService commonService, RecordService recordService) {
        this.userRepository = userRepository;
        this.todayExerciseRepository = todayExerciseRepository;
        this.attendanceRepository = attendanceRepository;
        this.exerciseRepository = exerciseRepository;
        this.commonService = commonService;
        this.recordService = recordService;
    }

    // 00시가 되면 오늘 운동 목록 삭제!!
    @Scheduled(cron = "0 0 0 * * ?")
    public void create(){
        System.out.println("todayexercise deleted!!!!!!!");
        todayExerciseRepository.deleteAll();
    }

    @Transactional
    public List<TodayExerciseDto> exerciseAdd(TodayExerciseDto todayExerciseDto) {

        User user = userRepository.findByEmail(commonService.getLoginUser().getEmail());
        Exercise exercise = exerciseRepository.findByName(todayExerciseDto.getExercise());
        // 이미 있는지 확인, 있으면 추가 안한 상태로 오늘의 운동 목록 반환
        List<TodayExercise> exercises = todayExerciseRepository.findByUser(user);
        if(exercises.size()>0){
            for(TodayExercise ex:exercises ){
                if(ex.getExercise().getName().equals(todayExerciseDto.getExercise())) {
                    return getTodayExerciseDtos(user);
                }
            }
        }
        // 새 운동 추가
        TodayExercise todayExercise = TodayExercise.builder()
                .user(user)
                .exercise(exercise)
                .build();
        todayExerciseRepository.save(todayExercise);

        return getTodayExerciseDtos(user);
    }

    // 개인의 오늘 운동 목록 조회
    public List<TodayExerciseDto> todayExercises(){
        User user = userRepository.findByEmail(commonService.getLoginUser().getEmail());
        return getTodayExerciseDtos(user);
    }

    // 개인 운동 하나 삭제
    public List<TodayExerciseDto> exerciseDelete(String exerciseToDelete) {

        User user = userRepository.findByEmail(commonService.getLoginUser().getEmail());
        Exercise exercise = exerciseRepository.findByName(exerciseToDelete);

        TodayExercise todayExercise = todayExerciseRepository.findByUserAndExercise(user, exercise).orElse(null);
        if(todayExercise==null) throw new EntityNotFoundException("없는 운동이에요");
        todayExerciseRepository.delete(todayExercise);

        return getTodayExerciseDtos(user);
    }

    // 개인 운동 하나 완료하기
    // 출석 체크
    public List<TodayExerciseDto> exerciseDone(String exerciseName, Long countCheck) {

        User user = userRepository.findByEmail(commonService.getLoginUser().getEmail());
        Exercise exercise = exerciseRepository.findByName(exerciseName);

        TodayExercise todayExercise = todayExerciseRepository.findByUserAndExercise(user, exercise).orElse(null);
        if (todayExercise==null){
            throw new EntityNotFoundException("없는 운동이에요");
        }
        todayExercise.setDone(true);
        todayExerciseRepository.save(todayExercise);

        Attendance attendance = attendanceRepository.findByUser(user);
        attendance.setDone(true);
        attendanceRepository.save(attendance);

        RecordDto recordDto = new RecordDto();
        recordDto.setCountCheck(countCheck);
        recordDto.setExercise(exerciseName);

        recordService.create(recordDto);

        return getTodayExerciseDtos(user);
    }

    // 개인 오늘 운동 조회 메서드
    private List<TodayExerciseDto> getTodayExerciseDtos(User user) {
        List<TodayExerciseDto> myExercises = new ArrayList<>();

        List<TodayExercise> exercises = todayExerciseRepository.findByUser(user);
        for (TodayExercise ex : exercises) {
            TodayExerciseDto todayExerciseDto = TodayExerciseDto.builder()
                    .exercise(ex.getExercise().getName())
                    .done(ex.isDone())
                    .build();
            myExercises.add(todayExerciseDto);
        }
        return myExercises;
    }




}

