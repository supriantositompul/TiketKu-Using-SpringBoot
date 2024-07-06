package com.kelempok7.serverapp.models.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrdersDtoRequest {
    private Integer quantity;
    private Integer scheduleRoomId;
    private String username;
    private List<Integer> chairNumber;
}
