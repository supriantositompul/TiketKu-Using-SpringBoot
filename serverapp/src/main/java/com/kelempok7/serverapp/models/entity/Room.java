package com.kelempok7.serverapp.models.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "room")
@Entity
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false,length = 15,unique = true)
    private String name;

    @Column(nullable = false,length = 3)
    private Integer capacity;

    @OneToMany(mappedBy = "room")
    @JsonIgnore
    private Set<ScheduleRoom> scheduleRooms;
}
