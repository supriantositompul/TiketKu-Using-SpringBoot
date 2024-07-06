package com.kelempok7.serverapp.models.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "chair")
@Entity
public class Chair {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "chair_number",nullable = false)
    private Integer chairNumber;

    @Column(name = "is_ordered",nullable = false)
    private Boolean isOrdered;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "order_id",nullable = false,referencedColumnName = "id")
    @JsonIgnore
    private Orders order;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "schedule_room_id",nullable = false,referencedColumnName = "id")
    private ScheduleRoom scheduleRoom;
}
