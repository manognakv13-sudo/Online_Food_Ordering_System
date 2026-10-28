package com.Project.Online_Food_Ordering_System.dto;

import java.math.BigDecimal;

public record MenuItemResponseDto(
        Long menuItemId,
        String name,
        String description,
        BigDecimal price,
        boolean available,
        Long restaurantId
) {
}
