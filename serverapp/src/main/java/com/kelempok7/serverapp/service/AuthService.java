package com.kelempok7.serverapp.service;

import com.kelempok7.serverapp.models.dto.request.RegistrationRequest;
import com.kelempok7.serverapp.models.dto.response.LoginRequest;
import com.kelempok7.serverapp.models.dto.response.LoginResponse;
import com.kelempok7.serverapp.models.entity.Role;
import com.kelempok7.serverapp.models.entity.User;

public interface AuthService {
    User registration(RegistrationRequest registrationRequest);
    User addRole(Integer userId, Role role);
    LoginResponse login(LoginRequest loginRequest);
}
