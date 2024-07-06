package com.kelempok7.serverapp.models.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudioRequest {

    @NotBlank(message = "Name Cannot Blank")
    @Size(min=2, max=50, message="Name length must be between 2 and 30 characters")
    @Pattern(regexp="[a-zA-Z0-9 ]+", message="Name can contain alphanumeric characters only")
    private String name;
}
