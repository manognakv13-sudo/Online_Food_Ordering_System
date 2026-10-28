package com.Project.Online_Food_Ordering_System.dto;

public record RestaurantResponseDto(
        Long restaurantId,
        String name,
        String address,
        String phone
) {
}
