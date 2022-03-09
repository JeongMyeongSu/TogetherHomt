package com.ssafy.togetherhomt.exercise.exercise;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ExerciseRepository extends JpaRepository<Exercise, Integer> {
    Exercise findByName(String name);
}
