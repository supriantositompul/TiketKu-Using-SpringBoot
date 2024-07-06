package com.kelempok7.serverapp.models.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FilmResponseGenre {
    private String name;
    private LocalDate releaseDate;
    private String poster;
    private String background;
    private double price;
    private String description;
}
