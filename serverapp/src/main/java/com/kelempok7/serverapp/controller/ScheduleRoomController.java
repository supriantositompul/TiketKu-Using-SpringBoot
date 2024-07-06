package com.kelempok7.serverapp.controller;

import com.kelempok7.serverapp.models.dto.request.OrderScheduleRoom;
import com.kelempok7.serverapp.models.dto.request.ScheduleRoomDtoRequest;
import com.kelempok7.serverapp.models.dto.response.CountEntityResponse;
import com.kelempok7.serverapp.models.entity.Chair;
import com.kelempok7.serverapp.models.entity.ScheduleRoom;
import com.kelempok7.serverapp.service.impl.ChairServiceImpl;
import com.kelempok7.serverapp.service.impl.ScheduleRoomServiceImpl;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;

@RestController
@RequestMapping("/schedule-room")
@AllArgsConstructor
@PreAuthorize("hasAnyRole('USER', 'ADMIN')")
public class ScheduleRoomController {
    private ScheduleRoomServiceImpl scheduleRoomService;
    private ChairServiceImpl chairService;

    @GetMapping
    @PreAuthorize("hasAnyAuthority('READ_USER', 'READ_ADMIN')")
    public List<ScheduleRoom> getAll() {
        return scheduleRoomService.getAll();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('READ_USER', 'READ_ADMIN')")
    public ScheduleRoom getById(@PathVariable Integer id) {
        return scheduleRoomService.getById(id);
    }
    @PostMapping
    @PreAuthorize("hasAnyAuthority('CREATE_USER', 'CREATE_ADMIN')")
    public ScheduleRoom create(@RequestBody ScheduleRoomDtoRequest scheduleRoomDtoRequest) {
        return scheduleRoomService.create(scheduleRoomDtoRequest);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('UPDATE_ADMIN')")
    public ScheduleRoom update(@PathVariable Integer id, @RequestBody ScheduleRoomDtoRequest scheduleRoomDtoRequest) {
        return scheduleRoomService.update(id, scheduleRoomDtoRequest);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('DELETE_ADMIN')")
    public ScheduleRoom delete(@PathVariable Integer id) {
        return scheduleRoomService.delete(id);
    }

    @GetMapping("/upcoming")
    @PreAuthorize("hasAuthority('READ_USER')")
    public List<ScheduleRoom> scheduleRoomUpComing(){
        return scheduleRoomService.getScheduleRoomComingSoon();
    }

    @GetMapping("/menu")
    @PreAuthorize("hasAnyAuthority('READ_USER')")
    public List<ScheduleRoom> ScheduleRoomMenu(){
        return scheduleRoomService.getScheduleRoomForMenu();
    }

    @GetMapping("/filter/{genre}")
    @PreAuthorize("hasAnyAuthority('READ_USER')")
    public List<ScheduleRoom> ScheduleRoomFilterByGenre(@PathVariable("genre") String genre){
        return scheduleRoomService.getScheduleByGenreFilter(genre);
    }

    @GetMapping("/search")
    @PreAuthorize("hasAnyAuthority('READ_USER')")
    public List<ScheduleRoom> ScheduleRoomFilterByFilmName(@RequestParam("keyword") String keyword){
        if(keyword != null){
            return scheduleRoomService.getScheduleByFilmName(keyword);
        }else {
            return scheduleRoomService.getScheduleRoomForMenu();
        }
    }
    @PostMapping("/order")
    public ScheduleRoom orderTicket(@RequestBody OrderScheduleRoom orderScheduleRoom){
        return scheduleRoomService.orderTicket(orderScheduleRoom);
    }

    @GetMapping("/chairs/{id}")
    @PreAuthorize("hasAnyAuthority('READ_ADMIN','READ_USER')")
    public List<Chair> getChairBaseScheduleRoom(@PathVariable("id") Integer id){
        return chairService.getChairBaseScheduleRoom(id);
    }

    @GetMapping("/count")
    @PreAuthorize("hasAnyAuthority('READ_ADMIN')")
    public ResponseEntity<CountEntityResponse> count(){
        return ResponseEntity.ok(scheduleRoomService.countScheduleRoom());
    }

}
