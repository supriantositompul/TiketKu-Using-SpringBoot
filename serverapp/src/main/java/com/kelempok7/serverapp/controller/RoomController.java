package com.kelempok7.serverapp.controller;

import com.kelempok7.serverapp.models.dto.request.RoomDtoRequest;
import com.kelempok7.serverapp.models.dto.request.StudioRequest;
import com.kelempok7.serverapp.models.dto.response.CountEntityResponse;
import com.kelempok7.serverapp.models.entity.Room;
import com.kelempok7.serverapp.models.entity.Studio;
import com.kelempok7.serverapp.service.impl.RoomServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/room")
@PreAuthorize("hasAnyRole('ADMIN','USER')")
public class RoomController {

    @Autowired
    private RoomServiceImpl roomService;

    @GetMapping
    public ResponseEntity<List<Room>> getAllRoom(){
        return ResponseEntity.ok(roomService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Room> getById(@PathVariable("id") Integer id){
        return ResponseEntity.ok(roomService.getById(id));
    }

    @PostMapping
    public ResponseEntity<Room> createRoom(@RequestBody @Valid RoomDtoRequest roomDtoRequest){
        return ResponseEntity.ok(roomService.create(roomDtoRequest));
    }

    @PutMapping("/{idRoom}")
    public ResponseEntity<Room> updateRoom(@PathVariable("idRoom") Integer idRoom, @RequestBody @Valid RoomDtoRequest roomDtoRequest){
        return ResponseEntity.ok(roomService.update(idRoom,roomDtoRequest));
    }

    @DeleteMapping("/{idRoom}")
    public ResponseEntity<Room> deleteRoom(@PathVariable("idRoom") Integer idRoom){
        return ResponseEntity.ok(roomService.delete(idRoom));
    }

    @GetMapping("/count")
    @PreAuthorize("hasAnyAuthority('READ_ADMIN')")
    public ResponseEntity<CountEntityResponse> count(){
        return ResponseEntity.ok(roomService.countRoom());
    }

}
