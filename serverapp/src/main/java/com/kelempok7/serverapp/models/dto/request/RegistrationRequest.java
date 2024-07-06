package com.kelempok7.serverapp.models.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegistrationRequest {
    @NotBlank(message = "Fullname Cannot Blank")
    @Size(min=3, max=50)
    @Pattern(message="Type can contain alpha characters only", regexp = "[a-zA-Z ]+")
    private String fullname;

    @NotBlank(message = "Email Cannot Blank")
    @Email(message="Invalid email address")
    private String email;

    @NotBlank(message = "Phone Cannot Blank")
    @Pattern(regexp="\\d{10,13}", message="Invalid phone number")
    private String phone;

    @NotBlank(message = "username cannot blank")
    @Size(min=3, max=50, message="Username length must be between 3 and 50 characters")
    @Pattern(regexp="[a-zA-Z0-9]+", message="Username can contain alphanumeric characters only")
    private String username;

    @Size(min=3, max=100, message="Address length must be between 3 and 100 characters")
    @Pattern(regexp="[a-zA-Z0-9., ]+", message="Address can contain alphanumeric characters only")
    private String address;

    @Size(min=8, max=50, message="Password length must be between 8 and 50 characters")
    @Pattern(regexp="[a-zA-Z0-9]+", message="Password can contain alphanumeric characters only")
    private String password;
}
