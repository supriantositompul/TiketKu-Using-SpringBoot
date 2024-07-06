package com.kelempok7.serverapp.service.impl;

import com.kelempok7.serverapp.handler.NotFoundExceptionHandler;
import com.kelempok7.serverapp.models.dto.request.GenreRequest;
import com.kelempok7.serverapp.models.dto.response.CountEntityResponse;
import com.kelempok7.serverapp.models.entity.Genre;
import com.kelempok7.serverapp.models.entity.Studio;
import com.kelempok7.serverapp.repository.GenreRepository;
import com.kelempok7.serverapp.service.GenericService;
import com.kelempok7.serverapp.service.GenericServiceDto;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class GenreServiceImpl implements GenericServiceDto<Genre,Integer, GenreRequest> {

    private GenreRepository genreRepository;

    @Override
    public List<Genre> getAll() {
        return genreRepository.findAll();
    }

    @Override
    public Genre getById(Integer id) {
        Optional<Genre> genre = genreRepository.findById(id);
        if (!genre.isPresent()){
            throw new NotFoundExceptionHandler("Genre Cannot Found");
        }
        return genre.get();
    }

    public CountEntityResponse countGenre(){
        return new CountEntityResponse("Genre",genreRepository.count());
    }

    @Override
    public Genre create(GenreRequest genreRequest) {
        Genre genre = new Genre();
        genre.setName(genreRequest.getName());
        return genreRepository.save(genre);
    }

    @Override
    public Genre update(Integer id, GenreRequest genreRequest) {
        getById(id);
        Genre genre = new Genre();
        genre.setId(id);
        genre.setName(genreRequest.getName());
        return genreRepository.save(genre);
    }

    @Override
    public Genre delete(Integer id) {
        Genre genre = getById(id);
        genreRepository.delete(genre);
        return genre;
    }

    public List<Genre> searchGenreByName(String keyword){
        return genreRepository.searchGenreByKeyword(keyword);
    }

    public Genre searchByName(String name){
        return genreRepository.findByName(name);
    }
}
