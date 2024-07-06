package com.kelempok7.serverapp.models.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderScheduleRoom {
    private Integer scheduleRoomId;
    private Integer quantity;
}
