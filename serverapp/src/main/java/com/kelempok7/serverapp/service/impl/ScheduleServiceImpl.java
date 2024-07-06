package com.kelempok7.serverapp.service.impl;

import com.kelempok7.serverapp.models.dto.request.ScheduleDtoRequest;
import com.kelempok7.serverapp.models.dto.response.CountEntityResponse;
import com.kelempok7.serverapp.models.entity.Room;
import com.kelempok7.serverapp.models.entity.Schedule;
import com.kelempok7.serverapp.repository.RoomRepository;
import com.kelempok7.serverapp.repository.ScheduleRepository;
import com.kelempok7.serverapp.service.GenericServiceDto;
import com.kelempok7.serverapp.service.UserService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class ScheduleServiceImpl implements GenericServiceDto<Schedule, Integer, ScheduleDtoRequest> {
    private final RoomRepository roomRepository;
    private ScheduleRepository scheduleRepository;
    private ModelMapper modelMapper;
    private FilmServiceImpl filmServiceImpl;
    private UserService userService;

    @Override
    public List<Schedule> getAll() {
        return scheduleRepository.findAll();
    }

    @Override
    public Schedule getById(Integer id) {
        return scheduleRepository.findById(id).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Schedule Id not found"));
    }

    @Override
    public Schedule create(ScheduleDtoRequest scheduleDtoRequest) {
        Schedule schedule = modelMapper.map(scheduleDtoRequest, Schedule.class);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        LocalDate date = LocalDate.parse(scheduleDtoRequest.getDate());
        System.out.println("Date IS : " + scheduleDtoRequest.getDate());
        LocalTime openTime = LocalTime.parse(scheduleDtoRequest.getOpenTime());
        LocalTime closeTime = LocalTime.parse(scheduleDtoRequest.getCloseTime());
        schedule.setUser(userService.findUserByUsername(username));
        schedule.setOpenTime(openTime);
        schedule.setCloseTime(closeTime);
        schedule.setDate(date);
        schedule.setUser(userService.findUserByUsername(username));
        schedule.setFilm(filmServiceImpl.getById(scheduleDtoRequest.getFilmId()));
        return scheduleRepository.save(schedule);
    }

    @Override
    public Schedule update(Integer id, ScheduleDtoRequest scheduleDtoRequest) {
        getById(id);
        Schedule schedule = modelMapper.map(scheduleDtoRequest, Schedule.class);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        schedule.setUser(userService.findUserByUsername(username));
        schedule.setId(id);
        LocalDate date = LocalDate.parse(scheduleDtoRequest.getDate());
        LocalTime openTime = LocalTime.parse(scheduleDtoRequest.getOpenTime());
        LocalTime closeTime = LocalTime.parse(scheduleDtoRequest.getCloseTime());
        schedule.setDate(date);
        schedule.setOpenTime(openTime);
        schedule.setCloseTime(closeTime);
        schedule.setFilm(filmServiceImpl.getById(scheduleDtoRequest.getFilmId()));
        return scheduleRepository.save(schedule);
    }

    public Schedule enableSchedule(Integer id){
        Schedule schedule = getById(id);
        schedule.setIsActive(true);
        scheduleRepository.save(schedule);
        return schedule;
    }

    public Schedule disableSchedule(Integer id){
        Schedule schedule = getById(id);
        schedule.setIsActive(false);
        scheduleRepository.save(schedule);
        return schedule;
    }

    public List<Schedule> findByActive(){
        return scheduleRepository.findByActive();
    }
    @Override
    public Schedule delete(Integer id) {
        Schedule schedule = getById(id);
        scheduleRepository.delete(schedule);
        return schedule;
    }

    public CountEntityResponse countSchedule(){
        return new CountEntityResponse("Schedule",scheduleRepository.count());
    }
}
