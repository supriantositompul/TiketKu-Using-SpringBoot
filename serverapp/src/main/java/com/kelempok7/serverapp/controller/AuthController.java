package com.kelempok7.serverapp.controller;



import com.kelempok7.serverapp.models.dto.request.ForgotPasswordRequest;
import com.kelempok7.serverapp.models.dto.request.RegistrationRequest;
import com.kelempok7.serverapp.models.dto.request.ResetPasswordRequest;
import com.kelempok7.serverapp.models.dto.response.LoginRequest;
import com.kelempok7.serverapp.models.dto.response.LoginResponse;
import com.kelempok7.serverapp.models.entity.PasswordResetToken;
import com.kelempok7.serverapp.models.entity.Role;
import com.kelempok7.serverapp.models.entity.User;
import com.kelempok7.serverapp.service.UserService;
import com.kelempok7.serverapp.service.impl.AuthServiceImpl;
import com.kelempok7.serverapp.service.impl.PasswordResetServiceImpl;

import lombok.AllArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*; 
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@AllArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthController {
    
    private AuthServiceImpl authService;
    private UserService userService;
    private PasswordResetServiceImpl passwordResetService;

    @PostMapping("/registration")
    public User registration(@RequestBody @Valid RegistrationRequest registrationRequest) {
        
        return authService.registration(registrationRequest);
    }

    @PutMapping("/add-role/{idEmployee}")
    public User addRole(
            @PathVariable Integer idEmployee,
            @RequestBody Role role
    ) {
        return authService.addRole(idEmployee, role);
    }

    @PostMapping("/login")
    public LoginResponse login(@RequestBody @Valid LoginRequest loginRequest){
        return authService.login(loginRequest);
    }

    @PostMapping("/forgotPassword")
    public ResponseEntity<String> forgotPassword (@RequestBody ForgotPasswordRequest forgotPasswordRequest, HttpServletRequest request){
        User user = userService.findUserByEmail(forgotPasswordRequest.getEmail());
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
        String token = UUID.randomUUID().toString();
        passwordResetService.createPasswordResetToken(user, token);
        String appUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
        passwordResetService.sendPasswordResetviaEmail(forgotPasswordRequest.getEmail(), appUrl, token);
        return ResponseEntity.ok("Password email reset sent");
    }

    @PostMapping("/resetPassword")
    public ResponseEntity<String> resetPassword (@RequestBody ResetPasswordRequest resetPasswordRequest){
        PasswordResetToken resetToken = passwordResetService.findByToken(resetPasswordRequest.getToken());
        if (resetToken == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("invalid token");
        }
        User user = resetToken.getUser();
        userService.updatePassword(user, resetPasswordRequest.getNewPassword());
        passwordResetService.delete(resetToken);

        return ResponseEntity.ok("Password successfully Reset");
    }


    @GetMapping("/check-email")
    public Boolean checkEmail(@RequestParam("email") String email){
        User userByEmail = userService.findUserByEmail(email);
        return userByEmail != null;
    }

    @GetMapping("/check-username")
    public Boolean checkUsername(@RequestParam("username") String username){
        User userByUsername = userService.findUserByUsername(username);
        return userByUsername != null;
    }
}
