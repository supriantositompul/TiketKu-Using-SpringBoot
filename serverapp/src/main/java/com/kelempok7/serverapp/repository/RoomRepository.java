package com.kelempok7.serverapp.repository;

import com.kelempok7.serverapp.models.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomRepository extends JpaRepository<Room,Integer> {
}
