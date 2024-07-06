package com.kelempok7.serverapp.models.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.jws.soap.SOAPBinding;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User {

    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false,unique = true)
    private String username;

    @Column(nullable = false,unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false,name = "fullname")
    private String fullName;

    @Column(nullable = false,length = 15)
    private String phone;

    @Column(columnDefinition = "TEXT")
    private String address;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "user_role",
            joinColumns = {
                    @JoinColumn(name = "user_id", referencedColumnName = "user_id",
                            nullable = false)},
            inverseJoinColumns = {
                    @JoinColumn(name = "role_id", referencedColumnName = "role_id",
                            nullable = false)})
    @JsonIgnore
    private List<Role> roles;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Orders> orders;

    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Schedule> schedules;

}
