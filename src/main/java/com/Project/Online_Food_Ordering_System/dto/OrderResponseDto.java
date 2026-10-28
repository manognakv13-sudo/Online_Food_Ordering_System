package com.Project.Online_Food_Ordering_System.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

public record OrderResponseDto(
        Long orderId,
        LocalDateTime orderDate,
        String status,
        BigDecimal totalAmount,
        Long customerId,
        Long restaurantId,
        Set<Long> menuItemIds
) {
}
