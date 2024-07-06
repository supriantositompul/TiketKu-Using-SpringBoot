package com.kelempok7.serverapp.repository;

import com.kelempok7.serverapp.models.entity.ScheduleRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.persistence.LockModeType;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface ScheduleRoomRepository extends JpaRepository<ScheduleRoom, Integer> {

    @Query(value = "SELECT sr FROM ScheduleRoom sr INNER JOIN Schedule s ON sr.schedule.id = s.id" +
            " WHERE s.date between :min AND :max AND s.isActive = true ORDER BY s.date")
    List<ScheduleRoom> getDateFilm(@Param("min") LocalDate min, @Param("max") LocalDate max);

    @Query(value = "SELECT sr FROM ScheduleRoom sr " +
            "INNER JOIN sr.schedule s " +
            "INNER JOIN s.film f " +
            "INNER JOIN f.genres g " +
            "WHERE s.date BETWEEN :min AND :max AND g.name = :genre")
    List<ScheduleRoom> getScheduleFilmByGenre(@Param("min") LocalDate min, @Param("max") LocalDate max, @Param("genre") String name);

    @Query(value = "SELECT sr FROM ScheduleRoom sr " +
            "INNER JOIN sr.schedule s " +
            "INNER JOIN s.film f " +
            "WHERE s.date BETWEEN :min AND :max AND f.name LIKE %:film%")
    List<ScheduleRoom> getScheduleFilmByFilmName(@Param("min") LocalDate min, @Param("max") LocalDate max, @Param("film") String film);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query(value = "SELECT sr FROM ScheduleRoom sr WHERE sr.id=:id")
    Optional<ScheduleRoom> getScheduleRoomByIdWithLocking(@Param("id") Integer id);
}