package com.kelempok7.serverapp.models.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ScheduleDtoRequest {
    private String date;
    private String openTime;
    private String closeTime;
    private Boolean isActive;
    private Integer filmId;
}
