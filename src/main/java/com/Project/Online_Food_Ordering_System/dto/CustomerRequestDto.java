package com.Project.Online_Food_Ordering_System.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record CustomerRequestDto(
        @NotBlank String name,
        @NotBlank @Email String email,
        String phone,
        String address
) {
}
