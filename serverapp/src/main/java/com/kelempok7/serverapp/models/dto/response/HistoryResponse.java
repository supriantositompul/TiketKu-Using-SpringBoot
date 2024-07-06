package com.kelempok7.serverapp.models.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HistoryResponse {
    private Integer orderId;
    private String filmName;
    private String filmBackground;
    private LocalDateTime orderDate;
    private double price;
    private double totalPrice;
    private Integer quantity;
}
