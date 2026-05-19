package com.example.laboratorio3.domain.dto.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ApiErrorResponse {
    private Integer status;
    private String message;
    private LocalDateTime timestamp;
    private String path;
}