package com.kelempok7.serverapp.models.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ScheduleRoomDtoRequest {
    private Integer roomId;
    private Integer scheduleId;
    private Integer ticket;
}
