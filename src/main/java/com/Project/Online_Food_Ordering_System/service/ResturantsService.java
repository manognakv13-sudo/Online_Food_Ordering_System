package com.Project.Online_Food_Ordering_System.service;

import com.Project.Online_Food_Ordering_System.dto.RestaurantRequestDto;
import com.Project.Online_Food_Ordering_System.dto.RestaurantResponseDto;
import com.Project.Online_Food_Ordering_System.entity.Resturants;
import com.Project.Online_Food_Ordering_System.exception.ResourceNotFoundException;
import com.Project.Online_Food_Ordering_System.repository.ResturantsRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ResturantsService {
    private final ResturantsRepository resturantsRepository;

    public ResturantsService(ResturantsRepository resturantsRepository) {
        this.resturantsRepository = resturantsRepository;
    }

    public List<RestaurantResponseDto> findAll() {
        return resturantsRepository.findAll().stream().map(this::toResponse).toList();
    }

    public RestaurantResponseDto findById(Long id) {
        return toResponse(getRestaurant(id));
    }

    public RestaurantResponseDto create(RestaurantRequestDto request) {
        Resturants restaurant = new Resturants();
        applyRequest(restaurant, request);
        return toResponse(resturantsRepository.save(restaurant));
    }

    public RestaurantResponseDto update(Long id, RestaurantRequestDto request) {
        Resturants restaurant = getRestaurant(id);
        applyRequest(restaurant, request);
        return toResponse(resturantsRepository.save(restaurant));
    }

    public void delete(Long id) {
        resturantsRepository.delete(getRestaurant(id));
    }

    private Resturants getRestaurant(Long id) {
        return resturantsRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Restaurant not found: " + id));
    }

    private void applyRequest(Resturants restaurant, RestaurantRequestDto request) {
        restaurant.setName(request.name());
        restaurant.setAddress(request.address());
        restaurant.setPhone(request.phone());
    }

    private RestaurantResponseDto toResponse(Resturants restaurant) {
        return new RestaurantResponseDto(
                restaurant.getRestaurantId(),
                restaurant.getName(),
                restaurant.getAddress(),
                restaurant.getPhone()
        );
    }
}
