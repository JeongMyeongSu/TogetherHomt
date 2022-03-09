package com.ssafy.togetherhomt.exercise.attendance;

import com.ssafy.togetherhomt.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, Long> {
    List<Attendance> findAllByUser(User user);
    List<Attendance> findAllByDoneFalse();
    Attendance findByUser(User user);
}
