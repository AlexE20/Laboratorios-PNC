package com.example.laboratorio3.service.impl;

import com.example.laboratorio3.common.mapper.SpecimenMapper;
import com.example.laboratorio3.domain.dto.request.specimen.CreateSpecimenRequest;
import com.example.laboratorio3.domain.dto.request.specimen.UpdateSpecimenRequest;
import com.example.laboratorio3.domain.dto.response.specimen.PageableResponse;
import com.example.laboratorio3.domain.dto.response.specimen.SpecimenResponse;
import com.example.laboratorio3.domain.entity.Specimen;
import com.example.laboratorio3.domain.exceptions.ResourceNotFoundException;
import com.example.laboratorio3.repository.SpecimenRepository;
import com.example.laboratorio3.service.SpecimenService;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SpecimenServiceImpl implements SpecimenService {

    private final SpecimenRepository specimenRepository;
    private final SpecimenMapper specimenMapper;

    @Override
    @Transactional
    public SpecimenResponse createSpecimen(CreateSpecimenRequest request) {

        Specimen specimen = specimenMapper.toEntityCreate(request);

        Specimen savedSpecimen = specimenRepository.save(specimen);

        return specimenMapper.toDto(savedSpecimen);
    }

    @Override
    public PageableResponse<SpecimenResponse> getAllSpecimens(
            int page,
            int size,
            String sortBy,
            String sortOrder
    ) {

        Sort sort = sortOrder.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);

        Page<Specimen> specimenPage =
                specimenRepository.findAll(pageable);

        Page<SpecimenResponse> responsePage =
                specimenPage.map(specimenMapper::toDto);

        return PageableResponse.<SpecimenResponse>builder()
                .content(responsePage.getContent())
                .page(responsePage.getNumber())
                .size(responsePage.getSize())
                .totalElements(responsePage.getTotalElements())
                .totalPages(responsePage.getTotalPages())
                .last(responsePage.isLast())
                .build();
    }

    @Override
    public SpecimenResponse getSpecimenById(UUID id) {

        Specimen specimen = specimenRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Specimen not found in Hyrule Records"
                        )
                );

        return specimenMapper.toDto(specimen);
    }

    @Override
    @Transactional
    public SpecimenResponse updateSpecimen(
            UUID id,
            UpdateSpecimenRequest request
    ) {

        this.getSpecimenById(id);

        Specimen specimen = specimenMapper.toEntityUpdate(request, id);

        Specimen updatedSpecimen =
                specimenRepository.save(specimen);

        return specimenMapper.toDto(updatedSpecimen);
    }

    @Override
    @Transactional
    public SpecimenResponse deleteSpecimen(UUID id) {

        SpecimenResponse existingSpecimen =
                this.getSpecimenById(id);

        specimenRepository.deleteById(id);

        return existingSpecimen;
    }
}