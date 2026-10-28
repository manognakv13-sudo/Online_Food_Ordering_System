package com.Project.Online_Food_Ordering_System.dto;

import java.time.LocalDateTime;
import java.util.Map;

public record ApiErrorDto(
        LocalDateTime timestamp,
        int status,
        String error,
        String message,
        Map<String, String> validationErrors
) {
}
