package com.kelempok7.serverapp.models.dto.request;



import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FilmDtoRequest {
    private String name;
    private String releaseDate;
    private String poster;
    private String background;
    private double price;
    private String description;
    private List<Integer> genresId;
    private List<Integer> studiosId;
}
