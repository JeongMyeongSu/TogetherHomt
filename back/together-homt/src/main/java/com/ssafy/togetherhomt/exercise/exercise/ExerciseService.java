package com.ssafy.togetherhomt.exercise.exercise;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ExerciseService {

    ExerciseRepository exerciseRepository;

    @Autowired
    public ExerciseService(ExerciseRepository exerciseRepository) {
        this.exerciseRepository = exerciseRepository;
    }

    String exerciseAdd(ExerciseDto exerciseDto){

        Exercise exercise = Exercise.builder()
                .name(exerciseDto.getName())
                .part(exerciseDto.getPart())
                .exerciseTime(exerciseDto.getExerciseTime())
                .exerciseCount(exerciseDto.getExerciseCount())
                .videoUrl(exerciseDto.getVideoUrl())
                .type(exerciseDto.getType())
                .build();

        exerciseRepository.save(exercise);
        return "success";
    }

    List<ExerciseDto> getExercise(){
        List<ExerciseDto> allExercise = new ArrayList<ExerciseDto>();

        for(Exercise exercise:exerciseRepository.findAll()){
            ExerciseDto exerciseDto = ExerciseDto.builder()
                    .name(exercise.getName())
                    .part(exercise.getPart())
                    .exerciseCount(exercise.getExerciseCount())
                    .exerciseTime(exercise.getExerciseTime())
                    .videoUrl(exercise.getVideoUrl())
                    .type(exercise.getType())
                    .build();
            allExercise.add(exerciseDto);
        }
        return allExercise;
    }

    String deleteExercise(String exercise){
        Exercise exercise1 = exerciseRepository.findByName(exercise);
        exerciseRepository.delete(exercise1);
        return "success";
    }

    public String exerciseUpdate(String exercise, ExerciseDto exerciseDto) {
        Exercise newExercise = exerciseRepository.findByName(exercise);
        if(exerciseDto.getName() != null) newExercise.setName(exerciseDto.getName());
        if(exerciseDto.getPart() != null) newExercise.setPart(exerciseDto.getPart());
        if(exerciseDto.getExerciseCount()!= null) newExercise.setExerciseCount(exerciseDto.getExerciseCount());
        if(exerciseDto.getExerciseTime()!= null) newExercise.setExerciseTime(exerciseDto.getExerciseTime());
        if(exerciseDto.getVideoUrl() != null) newExercise.setVideoUrl(exerciseDto.getVideoUrl());
        if(exerciseDto.getType() != null) newExercise.setType(exerciseDto.getType());

        exerciseRepository.save(newExercise);

        return "success";
    }
}
