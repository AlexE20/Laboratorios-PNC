package com.example.laboratorio3.domain.dto.response.specimen;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class PageableResponse<T> {
    private List<T> content;

    private Integer page;
    private Integer size;

    private Long totalElements;
    private Integer totalPages;

    private Boolean last;
}