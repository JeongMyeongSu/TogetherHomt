package com.ssafy.togetherhomt.exercise.todayExercise;

import com.ssafy.togetherhomt.exercise.exercise.Exercise;
import com.ssafy.togetherhomt.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TodayExerciseRepository extends JpaRepository<TodayExercise, Long> {
    List<TodayExercise> findByUser(User user);
    Optional<TodayExercise> findByUserAndExercise(User user, Exercise exercise);
}
