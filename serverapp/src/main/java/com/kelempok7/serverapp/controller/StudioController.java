package com.kelempok7.serverapp.controller;

import com.kelempok7.serverapp.models.dto.request.StudioRequest;
import com.kelempok7.serverapp.models.dto.response.CountEntityResponse;
import com.kelempok7.serverapp.models.entity.Studio;
import com.kelempok7.serverapp.service.impl.StudioServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/studio")
@PreAuthorize("hasAnyRole('ADMIN','USER')")
public class StudioController {

    @Autowired
    private StudioServiceImpl studioService;

    @GetMapping
    @PreAuthorize("hasAnyAuthority('READ_USER','READ_ADMIN')")
    public ResponseEntity<List<Studio>> getAll(){
        return ResponseEntity.ok(studioService.getAll());
    }

    @GetMapping("/check")
    @PreAuthorize("hasAnyAuthority('READ_ADMIN')")
    public ResponseEntity<Studio> checkDuplicateName(@Param("name") String name){
        return ResponseEntity.ok(studioService.searchByName(name));
    }

    @GetMapping("/{idStudio}")
    @PreAuthorize("hasAnyAuthority('READ_USER','READ_ADMIN')")
    public ResponseEntity<Studio> findStudioById(@PathVariable("idStudio") Integer idStudio){
        return ResponseEntity.ok(studioService.getById(idStudio));
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('CREATE_ADMIN')")
    public ResponseEntity<Studio> saveStudio(@RequestBody @Valid StudioRequest studioRequest){
        return ResponseEntity.ok(studioService.create(studioRequest));
    }

    @PutMapping("/{idStudio}")
    @PreAuthorize("hasAnyAuthority('UPDATE_ADMIN')")
    public ResponseEntity<Studio> updateStudio(@PathVariable("idStudio") Integer idStudio,@RequestBody @Valid StudioRequest studioRequest){
        return ResponseEntity.ok(studioService.update(idStudio,studioRequest));
    }

    @DeleteMapping("/{idStudio}")
    @PreAuthorize("hasAnyAuthority('DELETE_ADMIN')")
    public ResponseEntity<Studio> deleteStudio(@PathVariable("idStudio") Integer idStudio){
        return ResponseEntity.ok(studioService.delete(idStudio));
    }

    @GetMapping("/search")
    @PreAuthorize("hasAnyAuthority('READ_USER','READ_ADMIN')")
    public ResponseEntity<List<Studio>> searchStudio(@Param("keyword") String keyword){
        return ResponseEntity.ok(studioService.searchStudioByName(keyword));
    }

    @GetMapping("/count")
    @PreAuthorize("hasAnyAuthority('READ_ADMIN')")
    public ResponseEntity<CountEntityResponse> count(){
        return ResponseEntity.ok(studioService.countStudio());
    }
}
