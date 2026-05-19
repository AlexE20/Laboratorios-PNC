package com.example.laboratorio3.domain.dto.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class GeneralResponse<T> {
    private String message;
    private Integer status;
    private LocalDateTime timestamp;
    private String path;
    private T data;
}
