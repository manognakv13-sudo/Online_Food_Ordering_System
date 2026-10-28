package com.Project.Online_Food_Ordering_System.dto;

import jakarta.validation.constraints.NotBlank;

public record RestaurantRequestDto(
        @NotBlank String name,
        String address,
        String phone
) {
}
