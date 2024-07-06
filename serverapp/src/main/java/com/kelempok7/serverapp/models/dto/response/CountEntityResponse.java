package com.kelempok7.serverapp.models.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CountEntityResponse {
    private String name;
    private Long count;
}
