package com.kelempok7.serverapp.service.impl;

import com.kelempok7.serverapp.models.entity.Chair;
import com.kelempok7.serverapp.repository.ChairRepository;
import com.kelempok7.serverapp.service.GenericService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@AllArgsConstructor
public class ChairServiceImpl implements GenericService<Chair,Integer> {
    private ChairRepository chairRepository;

    @Override
    public List<Chair> getAll() {
        return chairRepository.findAll();
    }

    @Override
    public Chair getById(Integer id) {
        return chairRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Chair id Not Found"));
    }

    @Override
    public Chair create(Chair chair) {
        return chairRepository.save(chair);
    }

    public List<Chair>createAllChair(List<Chair> chairs){
        return chairRepository.saveAll(chairs);
    }

    @Override
    public Chair update(Integer id, Chair chair) {
        getById(id);
        chair.setId(id);
        return chairRepository.save(chair);
    }

    public List<Chair> getChairBaseScheduleRoom(Integer scheduleRoomId){
        return chairRepository.getChairBasedOnScheduleRoomSorted(scheduleRoomId);
    }

    @Override
    public Chair delete(Integer id) {
        Chair chair = getById(id);
        chairRepository.delete(chair);
        return chair;
    }


}
