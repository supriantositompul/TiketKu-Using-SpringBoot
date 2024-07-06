package com.kelempok7.serverapp.models.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "privilege")
public class Privilege {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false,unique = true)
    private String name;

    @ManyToMany(mappedBy = "privileges")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private List<Role> roles;
}
