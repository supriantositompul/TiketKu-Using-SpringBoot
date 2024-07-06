package com.kelempok7.serverapp.service;

import com.kelempok7.serverapp.models.entity.User;

public interface UserService {
    User findUserByEmail (String email);
    void updatePassword (User user, String newPassword);
    User findUserByUsername (String email);
}
