package com.kelempok7.serverapp.models.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Schedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private LocalDate date;

    @Column(nullable = false)
    private LocalTime openTime;

    @Column(nullable = false)
    private LocalTime closeTime;


    @Column(nullable = false)
    private Boolean isActive;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "admin_id",nullable = false,referencedColumnName = "user_id")
    private User user;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "film_id",nullable = false,referencedColumnName = "id")
    private Film film;

    @OneToMany(mappedBy = "schedule")
    @JsonIgnore
    private List<ScheduleRoom> scheduleRooms;
}
