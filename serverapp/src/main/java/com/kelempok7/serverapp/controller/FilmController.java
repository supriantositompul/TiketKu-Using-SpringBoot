package com.kelempok7.serverapp.controller;

import com.kelempok7.serverapp.models.dto.request.FilmDtoRequest;
import com.kelempok7.serverapp.models.dto.response.CountEntityResponse;
import com.kelempok7.serverapp.models.dto.response.FilmResponseGenre;
import com.kelempok7.serverapp.models.entity.Film;
import com.kelempok7.serverapp.models.entity.Genre;
import com.kelempok7.serverapp.service.impl.FilmServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/film")
@PreAuthorize("hasAnyRole('ADMIN', 'USER')")
public class FilmController {
    private FilmServiceImpl filmServiceImpl;

    @GetMapping
    @PreAuthorize("hasAnyAuthority('READ_ADMIN', 'READ_USER')")
    public ResponseEntity<List<Film>> getAll(){
        return ResponseEntity.ok(filmServiceImpl.getAll());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('READ_ADMIN', 'READ_USER')")
    public ResponseEntity<Film> getById(@PathVariable("id") Integer id){
        return ResponseEntity.ok(filmServiceImpl.getById(id));
    }

    @GetMapping("/carrousel")
    public ResponseEntity<List<Film>> getFilmForCarrousel(){
        return ResponseEntity.ok(filmServiceImpl.getFilmForCarrousel());
    }

    @GetMapping("/check")
    @PreAuthorize("hasAnyAuthority('READ_ADMIN')")
    public ResponseEntity<Film> checkDuplicateName(@Param("name") String name){
        return ResponseEntity.ok(filmServiceImpl.searchByName(name));
    }

    @PostMapping
    @PreAuthorize("hasAuthority('CREATE_ADMIN')")
    public ResponseEntity<Film> create(@RequestBody FilmDtoRequest filmDtoRequest){
        return ResponseEntity.ok(filmServiceImpl.create(filmDtoRequest));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('UPDATE_ADMIN')")
    public ResponseEntity<Film> update(@PathVariable("id") Integer id, @RequestBody FilmDtoRequest filmDtoRequest){
        return ResponseEntity.ok(filmServiceImpl.update(id, filmDtoRequest));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('DELETE_ADMIN')")
    public ResponseEntity<FilmResponseGenre> delete(@PathVariable("id") Integer id){
        return ResponseEntity.ok(filmServiceImpl.deleteFilm(id));
    }

    @GetMapping("/count")
    @PreAuthorize("hasAnyAuthority('READ_ADMIN')")
    public ResponseEntity<CountEntityResponse> count(){
        return ResponseEntity.ok(filmServiceImpl.countFilm());
    }
}