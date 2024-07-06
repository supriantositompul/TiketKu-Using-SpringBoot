package com.kelempok7.serverapp.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.kelempok7.serverapp.models.dto.request.UserProfileRequest;
import com.kelempok7.serverapp.models.entity.User;
import com.kelempok7.serverapp.repository.UserRepository;

@Service
public class ProfileService {
    
    @Autowired
    private UserRepository userRepository;

    public List<UserProfileRequest> findAll(){
        List<User> users = userRepository.findAll();
        return users.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    private UserProfileRequest convertToDTO(User user) {
        UserProfileRequest dto = new UserProfileRequest();
        dto.setUsername(user.getUsername());
        dto.setEmail(user.getEmail());
        dto.setFullName(user.getFullName());
        dto.setPhone(user.getPhone());
        dto.setAddress(user.getAddress());
        return dto;
    }

    public Optional<User> updateUserProfile(String username, UserProfileRequest profileRequest){
        Optional <User> userOptional = userRepository.findByUsernameOrEmail(username, username);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setFullName(profileRequest.getFullName());
            user.setPhone(profileRequest.getPhone());
            user.setAddress(profileRequest.getAddress());
            user.setEmail(profileRequest.getEmail());
            user.setUsername(profileRequest.getUsername());
            userRepository.save(user);
            return Optional.of(user);
        }
        return Optional.empty();
    }

    public User findByUsername(String username){
        return userRepository.findByUsernameOrEmail(username, username)
                             .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
    }

    public User findById(Integer id){
        return userRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public User delete(Integer id){
        User user = findById(id);

        user.getRoles().clear();
        userRepository.save(user);
        userRepository.delete(user);
        return user;
    }

}
