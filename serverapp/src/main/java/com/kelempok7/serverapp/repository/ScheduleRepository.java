package com.kelempok7.serverapp.repository;

import com.kelempok7.serverapp.models.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Integer> {
    //jpql for searching Active Schedule
    @Query("SELECT s FROM Schedule s Where s.isActive = true")
    List<Schedule> findByActive();
}

