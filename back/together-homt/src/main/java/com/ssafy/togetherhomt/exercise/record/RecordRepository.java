package com.ssafy.togetherhomt.exercise.record;

import com.ssafy.togetherhomt.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecordRepository extends JpaRepository<Record, Long> {

    List<Record> findAllByUser(User user);
    List<Record> findRecordsByUserAndDate(User user, String date);
}
