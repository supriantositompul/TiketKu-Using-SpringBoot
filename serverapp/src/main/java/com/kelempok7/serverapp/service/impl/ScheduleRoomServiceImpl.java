package com.kelempok7.serverapp.service.impl;

import com.kelempok7.serverapp.models.dto.request.OrderScheduleRoom;
import com.kelempok7.serverapp.models.dto.request.ScheduleRoomDtoRequest;
import com.kelempok7.serverapp.models.dto.response.CountEntityResponse;
import com.kelempok7.serverapp.models.entity.Room;
import com.kelempok7.serverapp.models.entity.Schedule;
import com.kelempok7.serverapp.models.entity.ScheduleRoom;
import com.kelempok7.serverapp.repository.RoomRepository;
import com.kelempok7.serverapp.repository.ScheduleRepository;
import com.kelempok7.serverapp.repository.ScheduleRoomRepository;
import com.kelempok7.serverapp.service.GenericServiceDto;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;


import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
@Service
@AllArgsConstructor
public class ScheduleRoomServiceImpl implements GenericServiceDto<ScheduleRoom, Integer, ScheduleRoomDtoRequest> {

    private final ScheduleRepository scheduleRepository;
    private final RoomRepository roomRepository;
    private ScheduleRoomRepository scheduleRoomRepository;
    private ModelMapper modelMapper;
    @Override
    public List<ScheduleRoom> getAll() {
        return scheduleRoomRepository.findAll();
    }

    @Override
    public ScheduleRoom getById(Integer id) {
        return scheduleRoomRepository.findById(id).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Schedule Room Not Found"));
    }

    @Override
    public ScheduleRoom create(ScheduleRoomDtoRequest scheduleRoomDtoRequest) {
        ScheduleRoom scheduleRoom = modelMapper.map(scheduleRoomDtoRequest, ScheduleRoom.class);
        scheduleRoom.setSchedule(getScheduleId(scheduleRoomDtoRequest.getScheduleId()));
        scheduleRoom.setRoom(getRoomId(scheduleRoomDtoRequest.getRoomId()));
        return scheduleRoomRepository.save(scheduleRoom);
    }

    private Schedule getScheduleId(Integer scheduleId){
        Schedule schedule = scheduleRepository.findById(scheduleId).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Schedule Not Found"));
        return schedule;
    }

    private Room getRoomId(Integer roomId){
        Room room = roomRepository.findById(roomId).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Room Not Found"));
        return room;
    }

    @Override
    public ScheduleRoom update(Integer id, ScheduleRoomDtoRequest scheduleRoomDtoRequest) {
        getById(id);
        ScheduleRoom scheduleRoom = modelMapper.map(scheduleRoomDtoRequest, ScheduleRoom.class);
        scheduleRoom.setSchedule(getScheduleId(scheduleRoomDtoRequest.getScheduleId()));
        scheduleRoom.setRoom(getRoomId(scheduleRoomDtoRequest.getRoomId()));
        scheduleRoom.setId(id);
        return scheduleRoomRepository.save(scheduleRoom);
    }

    public List<ScheduleRoom> getScheduleRoomForMenu(){
        Calendar calendar = Calendar.getInstance();
        LocalDate localDateStart = LocalDate.now();
        DayOfWeek dayOfWeek = localDateStart.getDayOfWeek();
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        Date date = calendar.getTime();
        localDateStart = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate localDateEnd = localDateStart.plusDays(7L);
        return scheduleRoomRepository.getDateFilm(localDateStart,localDateEnd);
    }


    public List<ScheduleRoom> getScheduleRoomComingSoon(){
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH,1);
        Date date = calendar.getTime();
        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate localDateStart = localDate.plusMonths(1);
        LocalDate localDateEnd = localDate.plusMonths(3);
        return scheduleRoomRepository.getDateFilm(localDateStart,localDateEnd);
    }

    public List<ScheduleRoom> getScheduleByGenreFilter(String genreName){
        LocalDate localDateStart = LocalDate.now();
        LocalDate localDateEnd = localDateStart.with(TemporalAdjusters.lastDayOfMonth());
        return scheduleRoomRepository.getScheduleFilmByGenre(localDateStart,localDateEnd,genreName);
    }

    public List<ScheduleRoom> getScheduleByFilmName(String filmName){
        LocalDate localDateStart = LocalDate.now();
        LocalDate localDateEnd = localDateStart.with(TemporalAdjusters.lastDayOfMonth());
        return scheduleRoomRepository.getScheduleFilmByFilmName(localDateStart,localDateEnd,filmName);
    }

    public CountEntityResponse countScheduleRoom(){
        return new CountEntityResponse("Schedule Room",scheduleRoomRepository.count());
    }

    public ScheduleRoom orderTicket(OrderScheduleRoom orderScheduleRoom){
        ScheduleRoom scheduleRoom = getById(orderScheduleRoom.getScheduleRoomId());
        Integer totalTicket = scheduleRoom.getTicket() - orderScheduleRoom.getQuantity();
        if(totalTicket <= 0){
            scheduleRoom.setTicket(0);
        }
        scheduleRoom.setTicket(totalTicket);
        return scheduleRoomRepository.save(scheduleRoom);
    }

    @Override
    public ScheduleRoom delete(Integer id) {
        ScheduleRoom scheduleRoom = getById(id);
        scheduleRoomRepository.delete(scheduleRoom);
        return scheduleRoom;
    }
}
