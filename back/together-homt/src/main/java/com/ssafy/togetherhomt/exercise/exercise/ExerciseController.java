package com.ssafy.togetherhomt.exercise.exercise;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/exercise/exercises")
public class ExerciseController {

    private ExerciseService exerciseService;

    @Autowired
    public ExerciseController(ExerciseService exerciseService) {
        this.exerciseService = exerciseService;
    }

    @PostMapping
    public ResponseEntity<?> exerciseAdd(@RequestBody ExerciseDto exerciseDto){
        String result = exerciseService.exerciseAdd(exerciseDto);
        if (result.equals("success")){
            return new ResponseEntity(result, HttpStatus.OK);
        }else{
            return (ResponseEntity<?>) ResponseEntity.badRequest();
        }
    }

    @GetMapping
    public ResponseEntity<?> getExercise(){
        List<ExerciseDto> exercises = exerciseService.getExercise();
        return new ResponseEntity(exercises, HttpStatus.OK);
    }

    @DeleteMapping("/{exercise-name}")
    public ResponseEntity<?> deleteExercise(@PathVariable("exercise-name") String exerciseName){
        String result = exerciseService.deleteExercise(exerciseName);
        return new ResponseEntity(result, HttpStatus.OK);
    }

    @PutMapping("/{exercise-name}")
    public ResponseEntity<?> exerciseUpdate(@PathVariable("exercise-name") String exerciseName, @RequestBody ExerciseDto exerciseDto){
        String result = exerciseService.exerciseUpdate(exerciseName, exerciseDto);
        if (result.equals("success")){
            return new ResponseEntity(result, HttpStatus.OK);
        }else{
            return (ResponseEntity<?>) ResponseEntity.badRequest();
        }
    }
}
