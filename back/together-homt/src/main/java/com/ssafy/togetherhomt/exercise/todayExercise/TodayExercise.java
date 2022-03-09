package com.ssafy.togetherhomt.exercise.todayExercise;

import com.ssafy.togetherhomt.exercise.exercise.Exercise;
import com.ssafy.togetherhomt.user.User;
import lombok.*;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "today_exercise")
public class TodayExercise {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "today_exercise_id")
    private Long todayExerciseId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "exercise_id")
    private Exercise exercise;

    @Column(name = "done")
    private boolean done;
}
