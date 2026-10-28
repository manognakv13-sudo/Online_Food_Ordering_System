package com.Project.Online_Food_Ordering_System.dto;

public record CustomerResponseDto(
        Long customerId,
        String name,
        String email,
        String phone,
        String address
) {
}
