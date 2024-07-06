package com.kelempok7.serverapp.models.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CountOrderDateDto {
    private LocalDate orderDate;
    private Long valueDate;
}
