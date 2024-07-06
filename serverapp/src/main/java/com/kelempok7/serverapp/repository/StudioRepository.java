package com.kelempok7.serverapp.repository;

import com.kelempok7.serverapp.models.entity.Studio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface StudioRepository extends JpaRepository<Studio,Integer> {
    @Query("SELECT s FROM Studio s WHERE s.name LIKE %:keyword%")
    List<Studio> searchStudioByKeyword(@Param("keyword") String keyword);
    Studio findByName(String name);
}
