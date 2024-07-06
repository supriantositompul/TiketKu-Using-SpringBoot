package com.kelempok7.serverapp.controller;


import com.kelempok7.serverapp.models.dto.request.OrderDtoUser;
import com.kelempok7.serverapp.models.dto.request.OrdersDtoRequest;
import com.kelempok7.serverapp.models.dto.response.CountEntityResponse;
import com.kelempok7.serverapp.models.dto.response.CountOrderDateDto;
import com.kelempok7.serverapp.models.dto.response.HistoryResponse;
import com.kelempok7.serverapp.models.entity.Orders;
import com.kelempok7.serverapp.service.impl.OrdersServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@RestController
@AllArgsConstructor
@RequestMapping("/orders")
@PreAuthorize("hasAnyRole('USER', 'ADMIN')")
public class OrdersController {
    private OrdersServiceImpl ordersServiceImpl;

    @GetMapping
    @PreAuthorize("hasAnyAuthority('READ_USER', 'READ_ADMIN')")
    public ResponseEntity<List<Orders>> getAllOrders() {
        return ResponseEntity.ok(ordersServiceImpl.getAll());
    }
//    @GetMapping("/active")
//    @PreAuthorize("hasAnyAuthority('READ_USER', 'READ_ADMIN')")
//    public ResponseEntity<List<Orders>> getActiveOrders() {
//        return ResponseEntity.ok(ordersServiceImpl.getAllActive());
//    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('READ_USER', 'READ_ADMIN')")
    public ResponseEntity<Orders> getOrdersById(@PathVariable("id") int id) {
        return ResponseEntity.ok(ordersServiceImpl.getById(id));
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('CREATE_ADMIN', 'CREATE_USER')")
    public ResponseEntity<Orders> createOrders(@RequestBody OrdersDtoRequest ordersDtoRequest) {
        return ResponseEntity.ok(ordersServiceImpl.create(ordersDtoRequest));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('UPDATE_ADMIN')")
    public ResponseEntity<Orders> updateOrders(@PathVariable("id") int id, @RequestBody OrdersDtoRequest ordersDtoRequest) {
        return ResponseEntity.ok(ordersServiceImpl.update(id, ordersDtoRequest));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('DELETE_ADMIN')")
    public ResponseEntity<Orders> deleteOrders(@PathVariable("id") int id) {
        return ResponseEntity.ok(ordersServiceImpl.delete(id));
    }

    @PutMapping("/pending/{id}")
    @PreAuthorize("hasAuthority('UPDATE_ADMIN')")
    public ResponseEntity<Orders> updateOrdersPending(@PathVariable("id") int id) {
        return ResponseEntity.ok(ordersServiceImpl.pendingStatus(id));
    }

    @PutMapping("/cancel/{id}")
    @PreAuthorize("hasAuthority('UPDATE_ADMIN')")
    public ResponseEntity<Orders> updateOrdersCancel(@PathVariable("id") int id) {
        return ResponseEntity.ok(ordersServiceImpl.cancelStatus(id));
    }

    @PutMapping("/complete/{id}")
    @PreAuthorize("hasAuthority('UPDATE_ADMIN')")
    public ResponseEntity<Orders> updateOrdersComplete(@PathVariable("id") int id) {
        return ResponseEntity.ok(ordersServiceImpl.completeStatus(id));
    }

    @PostMapping("/transaction")
    @PreAuthorize("hasAnyAuthority('READ_USER', 'READ_ADMIN')")
    public ResponseEntity<Orders> getOrdersByIdUser(@RequestBody OrderDtoUser orderDtoUser) {
        return ResponseEntity.ok(ordersServiceImpl.getOrderByUsernameAndId(orderDtoUser));
    }

    @GetMapping("/count")
    @PreAuthorize("hasAnyAuthority('READ_ADMIN')")
    public ResponseEntity<CountEntityResponse> count(){
        return ResponseEntity.ok(ordersServiceImpl.countOrder());
    }

    @GetMapping("/history")
    @PreAuthorize("hasAnyAuthority('READ_ADMIN', 'READ_USER')")
    public List<HistoryResponse> getOrdersByUsername() {
        return ordersServiceImpl.getHistory();
    }

    @GetMapping("/chart")
    @PreAuthorize("hasAnyAuthority('READ_ADMIN')")
    public ResponseEntity<List<CountOrderDateDto>> getChartOrder(){
        return ResponseEntity.ok(ordersServiceImpl.getOrderChart());
    }
}