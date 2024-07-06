package com.kelempok7.serverapp.controller;


import com.kelempok7.serverapp.models.dto.request.ScheduleDtoRequest;
import com.kelempok7.serverapp.models.dto.response.CountEntityResponse;
import com.kelempok7.serverapp.models.entity.Schedule;
import com.kelempok7.serverapp.service.impl.ScheduleServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@AllArgsConstructor
@RequestMapping("/schedule")
@PreAuthorize("hasAnyRole('USER', 'ADMIN')")
public class ScheduleController {
    private ScheduleServiceImpl scheduleServiceImpl;

    @GetMapping
    @PreAuthorize("hasAnyAuthority('READ_USER', 'READ_ADMIN')")
    public ResponseEntity<List<Schedule>> getAllSchedules() {
        return ResponseEntity.ok(scheduleServiceImpl.getAll());
    }

    @GetMapping("/active")
    @PreAuthorize("hasAnyAuthority('READ_USER', 'READ_ADMIN')")
    public ResponseEntity<List<Schedule>> findByActive() {
        return ResponseEntity.ok(scheduleServiceImpl.findByActive());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('READ_USER', 'READ_ADMIN')")
    public ResponseEntity<Schedule> getScheduleById(@PathVariable("id") int id) {
        return ResponseEntity.ok(scheduleServiceImpl.getById(id));
    }

    @GetMapping("/enable/{id}")
    @PreAuthorize("hasAnyAuthority('UPDATE_ADMIN')")
    public ResponseEntity<Schedule> enableScheduleById(@PathVariable("id") int id) {
        return ResponseEntity.ok(scheduleServiceImpl.enableSchedule(id));
    }

    @GetMapping("/disable/{id}")
    @PreAuthorize("hasAnyAuthority('UPDATE_ADMIN')")
    public ResponseEntity<Schedule> disableScheduleById(@PathVariable("id") int id) {
        return ResponseEntity.ok(scheduleServiceImpl.disableSchedule(id));
    }

    @PostMapping
    @PreAuthorize("hasAuthority('CREATE_ADMIN')")
    public ResponseEntity<Schedule> createSchedule(@RequestBody ScheduleDtoRequest scheduleDtoRequest) {
        return ResponseEntity.ok(scheduleServiceImpl.create(scheduleDtoRequest));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('UPDATE_ADMIN')")
    public ResponseEntity<Schedule> updateSchedule(@PathVariable("id") int id, @RequestBody ScheduleDtoRequest scheduleDtoRequest) {
        return ResponseEntity.ok(scheduleServiceImpl.update(id, scheduleDtoRequest));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('DELETE_ADMIN')")
    public ResponseEntity<Schedule> deleteSchedule(@PathVariable("id") int id) {
        return ResponseEntity.ok(scheduleServiceImpl.delete(id));
    }

    @GetMapping("/count")
    @PreAuthorize("hasAnyAuthority('READ_ADMIN')")
    public ResponseEntity<CountEntityResponse> count(){
        return ResponseEntity.ok(scheduleServiceImpl.countSchedule());
    }

}