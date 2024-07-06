package com.kelempok7.serverapp.models.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoomDtoRequest {

    @Size(min=2, max=15, message="Name Room length must be between 2 - 15 characters")
    @Pattern(regexp="[a-zA-Z0-9., ]+", message="Name Room can contain alphanumeric characters only")
    private String name;

    @NotNull(message = "Capacity cannot be null")
    @Max(value = 999, message = "Capacity cannot exceed 999")
    private Integer capacity;
}
