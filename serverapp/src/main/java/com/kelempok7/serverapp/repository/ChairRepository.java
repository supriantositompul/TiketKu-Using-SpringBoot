package com.kelempok7.serverapp.repository;

import com.kelempok7.serverapp.models.entity.Chair;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChairRepository extends JpaRepository<Chair,Integer> {
    @Query("SELECT c FROM Chair c WHERE c.scheduleRoom.id = :id ORDER BY c.chairNumber ASC")
    List<Chair> getChairBasedOnScheduleRoomSorted(@Param("id") Integer scheduleRoomId);
}