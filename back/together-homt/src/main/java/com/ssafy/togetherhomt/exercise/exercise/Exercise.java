package com.ssafy.togetherhomt.exercise.exercise;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ssafy.togetherhomt.exercise.record.Record;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import com.ssafy.togetherhomt.exercise.todayExercise.TodayExercise;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(
    name = "exercise",
    uniqueConstraints = {
            @UniqueConstraint(columnNames = "name")
    }
)
public class Exercise {

    @Id
    @Column(name = "exercise_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long exerciseId;

    @Column(unique = true, nullable = false)
    @NotNull
    private String name;

    @Column(name = "")
    private String part;

    @Column(name = "type")
    private String type;

    @Column(name = "exercise_time")
    private Long exerciseTime;

    @Column(name = "exercise_count")
    private Long exerciseCount;

    @Column(name = "video_url")
    private String videoUrl;

    @OneToMany(fetch = FetchType.LAZY)
    @JsonIgnoreProperties({"exercise"})
    private List<Record> records = new ArrayList<>();

    @OneToMany(mappedBy = "exercise")
    @JsonIgnoreProperties({"exercise"})
    private List<TodayExercise> todayExercises = new ArrayList<>();
}
