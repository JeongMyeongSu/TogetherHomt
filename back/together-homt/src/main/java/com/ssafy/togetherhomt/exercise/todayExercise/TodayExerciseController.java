package com.ssafy.togetherhomt.exercise.todayExercise;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/exercise/today-exercises")
public class TodayExerciseController {

    private TodayExerciseService todayExerciseService;

    @Autowired
    public TodayExerciseController(TodayExerciseService todayExerciseService) {
        this.todayExerciseService = todayExerciseService;
    }

    // 오늘의 운동 추가
    @PostMapping
    public ResponseEntity<?> exerciseAdd(@RequestBody TodayExerciseDto todayExerciseDto){
        List<TodayExerciseDto> result = todayExerciseService.exerciseAdd(todayExerciseDto);
        if(result.isEmpty()) return new ResponseEntity("이미 추가한 운동입니다.", HttpStatus.BAD_REQUEST);
        return new ResponseEntity(result, HttpStatus.OK);
    }

    // 오늘의 운동 제거
    @DeleteMapping("/{exercise-name}")
    public ResponseEntity<?> exerciseDelete(@PathVariable("exercise-name") String exerciseName ){
        List<TodayExerciseDto> result = todayExerciseService.exerciseDelete(exerciseName);
        return new ResponseEntity(result, HttpStatus.OK);
    }

    // 내가 오늘 할 운동 목록
    @GetMapping
    public ResponseEntity<?> personalTodayExercises(){
        List<TodayExerciseDto> result = todayExerciseService.todayExercises();
        return new ResponseEntity(result, HttpStatus.OK);
    }

    // 오늘 운동 완료
    @PutMapping("/{exercise-name}")
    public ResponseEntity<?> exerciseDone(@PathVariable("exercise-name") String exerciseName, @RequestParam("count-check") Long countCheck){
        List<TodayExerciseDto> result = todayExerciseService.exerciseDone(exerciseName, countCheck);
        return new ResponseEntity(result, HttpStatus.OK);
    }

}
