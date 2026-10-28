package com.Project.Online_Food_Ordering_System.controller;

import com.Project.Online_Food_Ordering_System.dto.RestaurantRequestDto;
import com.Project.Online_Food_Ordering_System.dto.RestaurantResponseDto;
import com.Project.Online_Food_Ordering_System.service.ResturantsService;
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
@RequestMapping("/api/restaurants")
public class ResturantsController {
    private final ResturantsService resturantsService;

    public ResturantsController(ResturantsService resturantsService) {
        this.resturantsService = resturantsService;
    }

    @GetMapping
    public List<RestaurantResponseDto> findAll() {
        return resturantsService.findAll();
    }

    @GetMapping("/{id}")
    public RestaurantResponseDto findById(@PathVariable Long id) {
        return resturantsService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RestaurantResponseDto create(@Valid @RequestBody RestaurantRequestDto request) {
        return resturantsService.create(request);
    }

    @PutMapping("/{id}")
    public RestaurantResponseDto update(@PathVariable Long id, @Valid @RequestBody RestaurantRequestDto request) {
        return resturantsService.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        resturantsService.delete(id);
    }
}
