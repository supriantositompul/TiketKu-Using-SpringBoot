package com.kelempok7.serverapp.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kelempok7.serverapp.models.dto.request.UserProfileRequest;
import com.kelempok7.serverapp.models.entity.User;
import com.kelempok7.serverapp.service.impl.ProfileService;
import com.kelempok7.serverapp.service.impl.UserServiceImpl;

@RestController
@RequestMapping("/profile")
@PreAuthorize("hasAnyRole('USER', 'ADMIN')")
public class ProfileController {
    
    @Autowired
    private ProfileService profileService;

    @Autowired
    private UserServiceImpl userServiceImpl;

    @PreAuthorize("hasAuthority('READ_ADMIN')")
    @GetMapping("/dto/all")
    public ResponseEntity<List<UserProfileRequest>> getAllDTO(){
        return ResponseEntity.ok(profileService.findAll());
    }

    @PreAuthorize("hasAuthority('READ_ADMIN')")
    @GetMapping("/all")
    public ResponseEntity<List<User>> getAll(){
        return ResponseEntity.ok(userServiceImpl.getAll());
    }

    @GetMapping
    public ResponseEntity<UserProfileRequest> getUserProfileByAuthentication(@AuthenticationPrincipal UserDetails userDetails, User user){
        user = profileService.findByUsername(userDetails.getUsername());
        UserProfileRequest dto = new UserProfileRequest();
        dto.setUsername(user.getUsername());
        dto.setEmail(user.getEmail());
        dto.setFullName(user.getFullName());
        dto.setPhone(user.getPhone());
        dto.setAddress(user.getAddress());
        return ResponseEntity.ok(dto);
    }


    @PutMapping("/{username}")
    public ResponseEntity<User> updateUserProfile(@PathVariable("username") String username, @RequestBody UserProfileRequest profileRequest){
        Optional<User> updatedUser = profileService.updateUserProfile(username, profileRequest);
        return updatedUser.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PreAuthorize("hasAuthority('READ_ADMIN')")
    @GetMapping("/username/{username}")
    public ResponseEntity<UserProfileRequest> findByUsername (@PathVariable String username){
        User user = profileService.findByUsername(username);
        UserProfileRequest userProfileRequest = new UserProfileRequest(
            user.getFullName(), 
            user.getEmail(), 
            user.getPhone(), 
            user.getAddress(), 
            user.getUsername()
            );
        return ResponseEntity.ok(userProfileRequest);
    }

    @PreAuthorize("hasAuthority('READ_ADMIN')")
    @GetMapping("/id/{id}")
    public ResponseEntity<UserProfileRequest> findById (@PathVariable Integer id){
        User user = profileService.findById(id);
        UserProfileRequest userProfileRequest = new UserProfileRequest(
            user.getFullName(),
            user.getEmail(),
            user.getAddress(),
            user.getPhone(),
            user.getUsername()
        );
        return ResponseEntity.ok(userProfileRequest);
    }

    @PreAuthorize("hasAuthority('DELETE_ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<User> delete(@PathVariable Integer id){
        return ResponseEntity.ok(profileService.delete(id));
    }


}
