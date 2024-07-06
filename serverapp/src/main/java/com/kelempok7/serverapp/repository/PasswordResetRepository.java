package com.kelempok7.serverapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kelempok7.serverapp.models.entity.PasswordResetToken;

public interface PasswordResetRepository extends JpaRepository <PasswordResetToken, Integer> {
    PasswordResetToken findByToken(String token);
}
