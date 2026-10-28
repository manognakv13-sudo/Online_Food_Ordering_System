package com.Project.Online_Food_Ordering_System.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

public record OrderRequestDto(
        LocalDateTime orderDate,
        @NotBlank String status,
        @NotNull @DecimalMin("0.0") BigDecimal totalAmount,
        @NotNull Long customerId,
        @NotNull Long restaurantId,
        Set<@NotNull Long> menuItemIds
) {
}
