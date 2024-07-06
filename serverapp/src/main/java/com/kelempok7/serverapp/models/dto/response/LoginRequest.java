package com.kelempok7.serverapp.models.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest {
    @NotBlank(message = "Username Cannot Blank")
    private String username;

    @NotBlank(message = "Password Cannot Blank")
    private String password;
}
