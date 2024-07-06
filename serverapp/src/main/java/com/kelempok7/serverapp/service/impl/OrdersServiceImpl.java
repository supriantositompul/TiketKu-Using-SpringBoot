package com.kelempok7.serverapp.service.impl;

import com.kelempok7.serverapp.models.dto.request.OrderDtoUser;
import com.kelempok7.serverapp.models.dto.request.OrdersDtoRequest;
import com.kelempok7.serverapp.models.dto.response.CountEntityResponse;
import com.kelempok7.serverapp.models.dto.response.CountOrderDateDto;
import com.kelempok7.serverapp.models.dto.response.HistoryResponse;
import com.kelempok7.serverapp.models.entity.*;
import com.kelempok7.serverapp.repository.FilmRepository;
import com.kelempok7.serverapp.repository.OrdersRepository;
import com.kelempok7.serverapp.repository.ScheduleRepository;
import com.kelempok7.serverapp.repository.ScheduleRoomRepository;
import com.kelempok7.serverapp.service.GenericServiceDto;
import com.kelempok7.serverapp.service.UserService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;


import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

@Service
@AllArgsConstructor

public class OrdersServiceImpl implements GenericServiceDto<Orders, Integer, OrdersDtoRequest> {

    private final ScheduleRepository scheduleRepository;
    private final ScheduleRoomRepository scheduleRoomRepository;
    private UserService userService;
    private OrdersRepository ordersRepository;
    private ScheduleRoomServiceImpl scheduleRoomService;
    private ModelMapper modelMapper;
    private ChairServiceImpl chairService;

    @Override
    public List<Orders> getAll() {
        return ordersRepository.findAll();
    }

    @Override
    public Orders getById(Integer integer) {
        return ordersRepository.findById(integer).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Orders Id Not Found"));
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Orders create(OrdersDtoRequest ordersDtoRequest) {
        Orders orders = new Orders();
        ScheduleRoom scheduleRoom =  scheduleRoomRepository.getScheduleRoomByIdWithLocking(ordersDtoRequest.getScheduleRoomId()).get();
        User user = userService.findUserByUsername(ordersDtoRequest.getUsername());
        orders.setQuantity(ordersDtoRequest.getQuantity());
        orders.setStatus("Pending");
        orders.setTotalPrice(totalPrice(scheduleRoom.getSchedule().getId(), ordersDtoRequest.getQuantity()));
        orders.setUser(user);
        orders.setDate(LocalDateTime.now());
        Orders orderSave = ordersRepository.save(orders);
        List<Chair> chairList = saveChairs(scheduleRoom, orderSave, ordersDtoRequest.getChairNumber());
        scheduleRoomRepository.save(scheduleRoom);
        return orders;
    }

    private List<Chair> saveChairs(ScheduleRoom scheduleRoom,Orders orders,List<Integer> chairNumber){
        List<Chair> chairs = new ArrayList<>();
        for (Integer i : chairNumber) {
            Chair chair = new Chair();
            chair.setScheduleRoom(scheduleRoom);
            chair.setIsOrdered(true);
            chair.setOrder(orders);
            chair.setChairNumber(i);
            chairs.add(chair);
        }
        return chairService.createAllChair(chairs);
    }

    public CountEntityResponse countOrder(){
        return new CountEntityResponse("Orders",ordersRepository.count());
    }

    private double totalPrice(Integer idSchedule,Integer quantity) {
        //Get price from Film on schedule
        Schedule schedule = scheduleRepository.findById(idSchedule).
                orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Schedule Id Not Found"));
        double price = schedule.getFilm().getPrice();
        //Set total price = price * quantity
        return price * quantity;
    }

    @Override
    public Orders update(Integer id, OrdersDtoRequest ordersDtoRequest) {
        getById(id);
        Orders orders = modelMapper.map(ordersDtoRequest, Orders.class);
        orders.setId(id);
        return ordersRepository.save(orders);
    }

    public Orders getOrderByUsernameAndId(OrderDtoUser orderDtoUser){
        return ordersRepository.getOrderByUsernameAndId(orderDtoUser.getUsername(),orderDtoUser.getOrderId()).orElseThrow(
                ()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Username And ID Order Not Match")
        );
    }

    @Override
    public Orders delete(Integer id) {
        Orders orders = getById(id);
        ordersRepository.delete(orders);
        return orders;
    }
    public Orders pendingStatus(Integer id) {
        Orders orders = getById(id);
        orders.setStatus("Pending");
        return ordersRepository.save(orders);
    }

    public Orders completeStatus(Integer id) {
        Orders orders = getById(id);
        orders.setStatus("Completed");
        return ordersRepository.save(orders);
    }

    public Orders cancelStatus(Integer id) {
        Orders orders = getById(id);
        orders.setStatus("Cancelled");
        return ordersRepository.save(orders);
    }

    public List<HistoryResponse> getHistory() {
        List<HistoryResponse> historyResponses = new ArrayList<>();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        List<Orders> orders = ordersRepository.findOrdersByUsername(username);
        for (Orders order : orders) {
            HistoryResponse historyResponse = new HistoryResponse();
            historyResponse.setOrderId(order.getId());
            historyResponse.setOrderDate(order.getDate());
            historyResponse.setQuantity(order.getQuantity());
            historyResponse.setTotalPrice(order.getTotalPrice());
            Schedule schedule = order.getChairs().get(0).getScheduleRoom().getSchedule();
            Film film = schedule.getFilm();
            historyResponse.setFilmName(film.getName());
            historyResponse.setFilmBackground(film.getBackground());
            historyResponse.setPrice(film.getPrice());
            historyResponses.add(historyResponse);
        }
        return historyResponses;
    }

    public List<CountOrderDateDto> getOrderChart(){
        Calendar calendar = Calendar.getInstance();
        LocalDate localDateStart = LocalDate.now();
        DayOfWeek dayOfWeek = localDateStart.getDayOfWeek();
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        Date date = calendar.getTime();
        localDateStart = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate localDateEnd = localDateStart.plusDays(7L);
        List<Object[]> result = ordersRepository.getOrderDate(localDateStart, localDateEnd);
        List<CountOrderDateDto> countOrderDateDtos = new ArrayList<>();
        for (Object[] objects : result) {
            LocalDate localDate = LocalDate.parse(String.valueOf(objects[0]));
            Long orderCount = Long.parseLong(String.valueOf(objects[1]));
            CountOrderDateDto countOrderDateDto = new CountOrderDateDto(localDate,orderCount);
            countOrderDateDtos.add(countOrderDateDto);
        }
        return countOrderDateDtos;
    }

}
