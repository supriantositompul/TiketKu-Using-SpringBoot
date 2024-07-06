package com.kelempok7.serverapp.service.impl;

import com.kelempok7.serverapp.models.dto.request.EmailRequest;
import com.kelempok7.serverapp.models.dto.request.RegistrationRequest;
import com.kelempok7.serverapp.models.dto.response.LoginRequest;
import com.kelempok7.serverapp.models.dto.response.LoginResponse;
import com.kelempok7.serverapp.models.entity.Role;
import com.kelempok7.serverapp.models.entity.User;
import com.kelempok7.serverapp.repository.UserRepository;
import com.kelempok7.serverapp.service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {

    private UserRepository userRepository;
    private RoleService roleService;
    private PasswordEncoder passwordEncoder;

    private AuthenticationManager authenticationManager;
    private AppUserDetailService appUserDetailService;
    private EmailServiceImpl emailServiceImpl;

    @Override
    public User registration(RegistrationRequest registrationRequest) {
        User user = new User();
        user.setFullName(registrationRequest.getFullname());
        user.setEmail(registrationRequest.getEmail());
        user.setUsername(registrationRequest.getUsername());
        user.setPhone(registrationRequest.getPhone());
        user.setAddress(registrationRequest.getAddress());
        user.setPassword(passwordEncoder.encode(registrationRequest.getPassword()));
        user.setRoles(Arrays.asList(roleService.getById(1)));

        User savedUser = userRepository.save(user);

        // jms
       EmailRequest emailRequest = new EmailRequest();
       emailRequest.setTo(savedUser.getEmail());
       emailRequest.setSubject("Thank You For Registration");
       emailRequest.setBody("");
       emailRequest.setName(savedUser.getFullName());

       emailServiceImpl.sendMessageWithAttachment(emailRequest);

       return savedUser;
    }

    @Override
    public User addRole(Integer userId, Role role) {
        User user = userRepository.findById(userId).get();
        List<Role> roles = user.getRoles();
        roles.add(roleService.getById(role.getId()));
        user.setRoles(roles);
        userRepository.save(user);
        return user;
    }

    @Override
    public LoginResponse login(LoginRequest loginRequest) {
        // todo: Check Username & Password => Authentication
        UsernamePasswordAuthenticationToken authReq = new UsernamePasswordAuthenticationToken(
                loginRequest.getUsername(),
                loginRequest.getPassword()
        );

        Authentication authentication = authenticationManager.authenticate(authReq);
        SecurityContext securityContext = SecurityContextHolder.getContext();
        securityContext.setAuthentication(authentication);

        UserDetails userDetails = appUserDetailService.loadUserByUsername(
                loginRequest.getUsername()
        );

        User user = userRepository.findByUsernameOrEmail(
                loginRequest.getUsername(),
                loginRequest.getUsername()
        ).get();

        // todo : Authorities For Response

        List<String> authorities = userDetails
                .getAuthorities()
                .stream()
                .map(authority -> authority.getAuthority())
                .collect(Collectors.toList());

        // Mapping Response
        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setUsername(userDetails.getUsername());
        loginResponse.setEmail(user.getEmail());
        loginResponse.setAuthorities(authorities);
        return loginResponse;
    }
}
