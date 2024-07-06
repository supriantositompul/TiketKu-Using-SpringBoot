package com.kelempok7.serverapp.models.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class ScheduleRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "schedule_id", referencedColumnName = "id")
    private Schedule schedule;

    @Column(nullable = false, length = 3)
    private Integer ticket;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "room_id", referencedColumnName = "id")
    private Room room;

    @OneToMany(mappedBy = "scheduleRoom")
    @JsonIgnore
    private List<Chair> chairs;
}
