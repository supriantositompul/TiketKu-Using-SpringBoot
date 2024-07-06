package com.kelempok7.serverapp.models.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Film {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 60, unique = true)
    private String name;

    @Column(nullable = false)
    private LocalDate releaseDate;

    @Column(nullable = false)
    private String poster;

    @Column(nullable = false)
    private String background;

    @Column(nullable = false)
    private double price;

    @Column(nullable = false,columnDefinition = "TEXT")
    private String description;

    @OneToMany(mappedBy = "film")
    @JsonIgnore
    private List<Schedule> schedules;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "genre_film",
            joinColumns = @JoinColumn(name = "film_id",referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "genre_id",referencedColumnName = "id")
    )
    private List<Genre> genres;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "studio_film",
            joinColumns = @JoinColumn(name = "film_id",referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "studio_id" , referencedColumnName = "id")
    )
    private List<Studio> studios;

}
