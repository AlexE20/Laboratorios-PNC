package com.example.laboratorio3.service;

import com.example.laboratorio3.domain.dto.request.specimen.CreateSpecimenRequest;
import com.example.laboratorio3.domain.dto.request.specimen.UpdateSpecimenRequest;
import com.example.laboratorio3.domain.dto.response.specimen.PageableResponse;
import com.example.laboratorio3.domain.dto.response.specimen.SpecimenResponse;

import java.util.UUID;

public interface SpecimenService {
    SpecimenResponse createSpecimen(CreateSpecimenRequest request);
    PageableResponse<SpecimenResponse> getAllSpecimens(
            int page,
            int size,
            String sortBy,
            String sortOrder
    );

    SpecimenResponse getSpecimenById(UUID id);

    SpecimenResponse updateSpecimen(
            UUID id,
            UpdateSpecimenRequest request
    );

    SpecimenResponse deleteSpecimen(UUID id);
}
