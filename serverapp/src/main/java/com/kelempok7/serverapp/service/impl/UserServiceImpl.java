package com.kelempok7.serverapp.service.impl;


import java.util.List;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.kelempok7.serverapp.models.entity.User;
import com.kelempok7.serverapp.repository.UserRepository;
import com.kelempok7.serverapp.service.UserService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService{
    private UserRepository userRepository;
    
    @Override
    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
        
    }
    @Override
    public void updatePassword(User user, String newPassword) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(newPassword);
        
        user.setPassword(encodedPassword);
        userRepository.save(user);
    }

    public List<User> getAll(){
        return userRepository.findAll();
    }

    @Override
    public User findUserByUsername(String username){
        return userRepository.findByUsername(username);
    }
    
}
