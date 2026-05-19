package com.example.laboratorio3.controller;




import com.example.laboratorio3.domain.dto.request.specimen.CreateSpecimenRequest;
import com.example.laboratorio3.domain.dto.request.specimen.UpdateSpecimenRequest;
import com.example.laboratorio3.domain.dto.response.GeneralResponse;
import com.example.laboratorio3.domain.dto.response.specimen.PageableResponse;
import com.example.laboratorio3.domain.dto.response.specimen.SpecimenResponse;
import com.example.laboratorio3.service.SpecimenService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.UUID;

@RestController
@RequestMapping("/api/specimens")
@RequiredArgsConstructor
public class SpecimenController {

    private final SpecimenService specimenService;

    @PostMapping
    public ResponseEntity<GeneralResponse<SpecimenResponse>> createSpecimen(
            @Valid @RequestBody CreateSpecimenRequest request,
            HttpServletRequest httpRequest
    ) {

        SpecimenResponse response =
                specimenService.createSpecimen(request);

        return buildResponse(
                "Specimen created successfully",
                HttpStatus.CREATED,
                response,
                httpRequest
        );
    }

    @GetMapping
    public ResponseEntity<GeneralResponse<PageableResponse<SpecimenResponse>>> getAllSpecimens(

            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "name") String sortBy,
            @RequestParam(defaultValue = "asc") String sortOrder,

            HttpServletRequest httpRequest
    ) {

        PageableResponse<SpecimenResponse> response =
                specimenService.getAllSpecimens(
                        page,
                        size,
                        sortBy,
                        sortOrder
                );

        return buildResponse(
                "Specimens retrieved successfully",
                HttpStatus.OK,
                response,
                httpRequest
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<GeneralResponse<SpecimenResponse>> getSpecimenById(
            @PathVariable UUID id,
            HttpServletRequest httpRequest
    ) {

        SpecimenResponse response =
                specimenService.getSpecimenById(id);

        return buildResponse(
                "Specimen retrieved successfully",
                HttpStatus.OK,
                response,
                httpRequest
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<GeneralResponse<SpecimenResponse>> updateSpecimen(
            @PathVariable UUID id,
            @RequestBody UpdateSpecimenRequest request,
            HttpServletRequest httpRequest
    ) {

        SpecimenResponse response =
                specimenService.updateSpecimen(id, request);

        return buildResponse(
                "Specimen updated successfully",
                HttpStatus.OK,
                response,
                httpRequest
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<GeneralResponse<SpecimenResponse>> deleteSpecimen(
            @PathVariable UUID id,
            HttpServletRequest httpRequest
    ) {

        SpecimenResponse response =
                specimenService.deleteSpecimen(id);

        return buildResponse(
                "Specimen deleted successfully",
                HttpStatus.OK,
                response,
                httpRequest
        );
    }

    private <T> ResponseEntity<GeneralResponse<T>> buildResponse(
            String message,
            HttpStatus status,
            T data,
            HttpServletRequest request
    ) {

        GeneralResponse<T> response =
                GeneralResponse.<T>builder()
                        .message(message)
                        .status(status.value())
                        .timestamp(LocalDateTime.now())
                        .path(request.getRequestURI())
                        .data(data)
                        .build();

        return ResponseEntity
                .status(status)
                .body(response);
    }
}
