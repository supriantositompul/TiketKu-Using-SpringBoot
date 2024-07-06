package com.kelempok7.serverapp.repository;

import com.kelempok7.serverapp.models.entity.Film;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FilmRepository extends JpaRepository<Film, Integer> {

    Film findByName(String name);

    @Query(value = "SELECT * FROM Film ORDER BY RAND() LIMIT 3", nativeQuery = true)
    List<Film> getFilmForCarrousel();
}
