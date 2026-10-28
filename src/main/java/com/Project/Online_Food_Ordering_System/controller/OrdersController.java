package com.Project.Online_Food_Ordering_System.controller;

import com.Project.Online_Food_Ordering_System.dto.OrderRequestDto;
import com.Project.Online_Food_Ordering_System.dto.OrderResponseDto;
import com.Project.Online_Food_Ordering_System.service.OrdersService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrdersController {
    private final OrdersService ordersService;

    public OrdersController(OrdersService ordersService) {
        this.ordersService = ordersService;
    }

    @GetMapping
    public List<OrderResponseDto> findAll() {
        return ordersService.findAll();
    }

    @GetMapping("/{id}")
    public OrderResponseDto findById(@PathVariable Long id) {
        return ordersService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OrderResponseDto create(@Valid @RequestBody OrderRequestDto request) {
        return ordersService.create(request);
    }

    @PutMapping("/{id}")
    public OrderResponseDto update(@PathVariable Long id, @Valid @RequestBody OrderRequestDto request) {
        return ordersService.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        ordersService.delete(id);
    }
}
