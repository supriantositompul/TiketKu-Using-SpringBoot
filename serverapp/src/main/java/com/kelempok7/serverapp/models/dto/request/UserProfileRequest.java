package com.kelempok7.serverapp.models.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserProfileRequest {
    private String fullName;
    private String phone;
    private String address;
    private String email;
    private String username;
}
