package com.example.laboratorio3.common.mapper;

import com.example.laboratorio3.domain.dto.request.specimen.CreateSpecimenRequest;
import com.example.laboratorio3.domain.dto.request.specimen.UpdateSpecimenRequest;
import com.example.laboratorio3.domain.dto.response.specimen.SpecimenResponse;
import com.example.laboratorio3.domain.entity.Specimen;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class SpecimenMapper {

    public Specimen toEntityCreate(CreateSpecimenRequest request) {
        return Specimen.builder()
                .name(request.getName())
                .region(request.getRegion())
                .dangerLevel(request.getDangerLevel())
                .isFriendly(request.getIsFriendly())
                .build();
    }

    public Specimen toEntityUpdate(UpdateSpecimenRequest request, UUID id) {
        return Specimen.builder()
                .id(id)
                .name(request.getName())
                .region(request.getRegion())
                .dangerLevel(request.getDangerLevel())
                .isFriendly(request.getIsFriendly())
                .build();
    }

    public SpecimenResponse toDto(Specimen specimen) {
        return SpecimenResponse.builder()
                .id(specimen.getId())
                .name(specimen.getName())
                .region(specimen.getRegion())
                .dangerLevel(specimen.getDangerLevel())
                .isFriendly(specimen.getIsFriendly())
                .build();
    }

    // TODO: El estudiante deberá agregar aquí el método para mapear un Page<Specimen> a Page<SpecimenResponse>
    // pista: utilizando .map(this::toDto)
}
