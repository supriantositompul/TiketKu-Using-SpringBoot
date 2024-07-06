package com.kelempok7.serverapp.repository;

import com.kelempok7.serverapp.models.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {

    public Optional<User> findByUsernameOrEmail(String username, String password);
    public User findByEmail(String email);
    User findByUsername(String username);
}
