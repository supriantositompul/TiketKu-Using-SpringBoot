package com.kelempok7.serverapp.repository;

import com.kelempok7.serverapp.models.entity.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface GenreRepository extends JpaRepository<Genre,Integer> {
    @Query("SELECT g FROM Genre g WHERE g.name LIKE %:keyword%")
    List<Genre> searchGenreByKeyword(@Param("keyword") String keyword);
    Genre findByName(String name);
}
