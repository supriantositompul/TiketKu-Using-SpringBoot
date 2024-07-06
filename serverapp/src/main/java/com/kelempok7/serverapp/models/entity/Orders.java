package com.kelempok7.serverapp.models.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private double totalPrice;

    @Column(nullable = false)
    private Integer quantity;

    @Column(nullable = false)
    private LocalDateTime date;

    @Column(nullable = false, length = 15)
    private String status;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id",nullable = false,referencedColumnName = "user_id")
    private User user;

    @OneToMany(mappedBy = "order",cascade = CascadeType.ALL)
    private List<Chair> chairs;
}
