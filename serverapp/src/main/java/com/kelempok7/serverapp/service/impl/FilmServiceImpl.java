package com.kelempok7.serverapp.service.impl;

import com.kelempok7.serverapp.models.dto.request.FilmDtoRequest;
import com.kelempok7.serverapp.models.dto.response.CountEntityResponse;
import com.kelempok7.serverapp.models.dto.response.FilmResponseGenre;
import com.kelempok7.serverapp.models.entity.Film;
import com.kelempok7.serverapp.models.entity.Genre;
import com.kelempok7.serverapp.models.entity.Studio;
import com.kelempok7.serverapp.repository.FilmRepository;
import com.kelempok7.serverapp.repository.GenreRepository;
import com.kelempok7.serverapp.repository.StudioRepository;
import com.kelempok7.serverapp.service.GenericServiceDto;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class FilmServiceImpl implements GenericServiceDto<Film, Integer, FilmDtoRequest> {

    private final GenreRepository genreRepository;
    private final StudioRepository studioRepository;
    private FilmRepository filmRepository;
    private ModelMapper modelMapper;


    @Override
    public List<Film> getAll() {
        return filmRepository.findAll();
    }

    @Override
    public Film getById(Integer id) {
        return filmRepository.findById(id).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Film id is not found"));
    }

    @Override
    public Film create(FilmDtoRequest filmDtoRequest) {
        Film film = modelMapper.map(filmDtoRequest, Film.class);
        LocalDate releaseDate = LocalDate.parse(filmDtoRequest.getReleaseDate());
        film.setReleaseDate(releaseDate);
        film.setGenres(getGenresFromRequest(filmDtoRequest.getGenresId()));
        film.setStudios(getStudiosFromRequest(filmDtoRequest.getStudiosId()));
        return filmRepository.save(film);
    }

    @Override
    public Film update(Integer id, FilmDtoRequest filmDtoRequest) {
        getById(id);
        Film film = modelMapper.map(filmDtoRequest, Film.class);
        LocalDate releaseDate = LocalDate.parse(filmDtoRequest.getReleaseDate());
        film.setReleaseDate(releaseDate);
        film.setGenres(getGenresFromRequest(filmDtoRequest.getGenresId()));
        film.setStudios(getStudiosFromRequest(filmDtoRequest.getStudiosId()));
        film.setId(id);
        return filmRepository.save(film);
    }

    public CountEntityResponse countFilm(){
        return new CountEntityResponse("Film",filmRepository.count());
    }

    public Film searchByName(String name){
        return filmRepository.findByName(name);
    }

    private List<Genre> getGenresFromRequest(List<Integer> genresId) {
        List<Genre> genres = new ArrayList<>();
        for (Integer genreId : genresId) {
            Genre genre = genreRepository.findById(genreId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Genre id not found"));
            genres.add(genre);
        }
        return genres;
    }


    public List<Film> getFilmForCarrousel(){
        return filmRepository.getFilmForCarrousel();
    }

    private List<Studio> getStudiosFromRequest(List<Integer> studiosId) {
        List<Studio> studios = new ArrayList<>();
        for (Integer studioId : studiosId) {
            Studio studio = studioRepository.findById(studioId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Studio not found"));
            studios.add(studio);
        }
        return studios;
    }
    @Override
    public Film delete(Integer id) {
        return null;
    }

    public FilmResponseGenre deleteFilm(Integer id){
        Film film = getById(id);
        filmRepository.delete(film);
        if(!filmRepository.findById(id).isPresent()){
            FilmResponseGenre filmResponseGenre = new FilmResponseGenre();
            filmResponseGenre.setName(film.getName());
            filmResponseGenre.setBackground(film.getBackground());
            filmResponseGenre.setPrice(film.getPrice());
            filmResponseGenre.setDescription(film.getDescription());
            filmResponseGenre.setReleaseDate(film.getReleaseDate());
            filmResponseGenre.setPoster(film.getPoster());
            return filmResponseGenre;
        }
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Cannot Delete Film");
    }
}
