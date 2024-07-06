package com.kelempok7.serverapp.service.impl;

import com.kelempok7.serverapp.models.dto.request.RoomDtoRequest;
import com.kelempok7.serverapp.models.dto.response.CountEntityResponse;
import com.kelempok7.serverapp.models.entity.Chair;
import com.kelempok7.serverapp.models.entity.Room;
import com.kelempok7.serverapp.repository.RoomRepository;
import com.kelempok7.serverapp.service.GenericServiceDto;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@AllArgsConstructor
public class RoomServiceImpl implements GenericServiceDto<Room,Integer, RoomDtoRequest> {
    private RoomRepository roomRepository;
    @Override
    public List<Room> getAll() {
        return roomRepository.findAll();
    }

    @Override
    public Room getById(Integer id) {
        return roomRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Room Id Not Found"));
    }

    @Override
    public Room create(RoomDtoRequest roomDtoRequest) {
        Room room = new Room();
        room.setName(roomDtoRequest.getName());
        room.setCapacity(roomDtoRequest.getCapacity());
        return roomRepository.save(room);
    }

    public CountEntityResponse countRoom(){
        return new CountEntityResponse("Room",roomRepository.count());
    }

    @Override
    public Room update(Integer id, RoomDtoRequest roomDtoRequest) {
        Room byId = getById(id);
        byId.setId(id);
        byId.setName(roomDtoRequest.getName());
        byId.setCapacity(roomDtoRequest.getCapacity());
        return roomRepository.save(byId);
    }

    @Override
    public Room delete(Integer id) {
        Room room = getById(id);
        roomRepository.delete(room);
        return room;
    }

}
