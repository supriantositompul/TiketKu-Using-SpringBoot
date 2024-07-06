package com.kelempok7.serverapp.controller;

import com.kelempok7.serverapp.models.dto.request.GenreRequest;
import com.kelempok7.serverapp.models.dto.request.StudioRequest;
import com.kelempok7.serverapp.models.dto.response.CountEntityResponse;
import com.kelempok7.serverapp.models.entity.Genre;
import com.kelempok7.serverapp.models.entity.Studio;
import com.kelempok7.serverapp.service.impl.GenreServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/genre")
@PreAuthorize("hasAnyRole('USER','ADMIN')")
public class GenreController {

    @Autowired
    private GenreServiceImpl genreService;

    @GetMapping
    @PreAuthorize("hasAnyAuthority('READ_USER','READ_ADMIN')")
    public ResponseEntity<List<Genre>> getAll(){
        return ResponseEntity.ok(genreService.getAll());
    }

    @GetMapping("/{idGenre}")
    @PreAuthorize("hasAnyAuthority('READ_USER','READ_ADMIN')")
    public ResponseEntity<Genre> findStudioById(@PathVariable("idGenre") Integer idGenre){
        return ResponseEntity.ok(genreService.getById(idGenre));
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('CREATE_ADMIN')")
    public ResponseEntity<Genre> saveStudio(@RequestBody @Valid GenreRequest genreRequest){
        return ResponseEntity.ok(genreService.create(genreRequest));
    }

    @PutMapping("/{idGenre}")
    @PreAuthorize("hasAnyAuthority('UPDATE_ADMIN')")
    public ResponseEntity<Genre> updateStudio(@PathVariable("idGenre") Integer idGenre,@RequestBody @Valid GenreRequest genreRequest){
        return ResponseEntity.ok(genreService.update(idGenre,genreRequest));
    }

    @GetMapping("/check")
    @PreAuthorize("hasAnyAuthority('READ_ADMIN')")
    public ResponseEntity<Genre> checkDuplicateName(@Param("name") String name){
        return ResponseEntity.ok(genreService.searchByName(name));
    }

    @GetMapping("/count")
    @PreAuthorize("hasAnyAuthority('READ_ADMIN')")
    public ResponseEntity<CountEntityResponse> count(){
        return ResponseEntity.ok(genreService.countGenre());
    }
    @DeleteMapping("/{idGenre}")
    @PreAuthorize("hasAnyAuthority('DELETE_ADMIN')")
    public ResponseEntity<Genre> deleteStudio(@PathVariable("idGenre") Integer idGenre){
        return ResponseEntity.ok(genreService.delete(idGenre));
    }

    @GetMapping("/search")
    @PreAuthorize("hasAnyAuthority('READ_USER','READ_ADMIN')")
    public ResponseEntity<List<Genre>> searchStudio(@Param("keyword") String keyword){
        return ResponseEntity.ok(genreService.searchGenreByName(keyword));
    }
}
