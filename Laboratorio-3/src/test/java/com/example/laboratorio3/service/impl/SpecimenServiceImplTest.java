package com.example.laboratorio3.service.impl;

import com.example.laboratorio3.common.mapper.SpecimenMapper;
import com.example.laboratorio3.domain.dto.request.specimen.CreateSpecimenRequest;
import com.example.laboratorio3.domain.dto.request.specimen.UpdateSpecimenRequest;
import com.example.laboratorio3.domain.dto.response.specimen.PageableResponse;
import com.example.laboratorio3.domain.dto.response.specimen.SpecimenResponse;
import com.example.laboratorio3.domain.entity.Specimen;
import com.example.laboratorio3.domain.exceptions.ResourceNotFoundException;
import com.example.laboratorio3.repository.SpecimenRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SpecimenServiceImplTest {

    @Mock
    private SpecimenMapper specimenMapper;

    @Mock
    private SpecimenRepository specimenRepository;

    @InjectMocks
    private SpecimenServiceImpl specimenServices;

    private UUID specimenId;
    private Specimen specimen;
    private SpecimenResponse specimenResponse;

    @BeforeEach
    void setUp() {
        specimenId = UUID.randomUUID();

        specimen = Specimen.builder()
                .id(specimenId)
                .name("Bokoblin")
                .region("Eldin")
                .dangerLevel(3)
                .isFriendly(false)
                .build();

        specimenResponse = new SpecimenResponse(
                specimenId,
                "Bokoblin",
                "Eldin",
                3,
                false
        );
    }

    // ------------------------------------------------------------------ //
    //  createSpecimen
    // ------------------------------------------------------------------ //

    @Test
    @DisplayName("createSpecimen: debe mapear, guardar y retornar el DTO correctamente")
    void createSpecimen_shouldSaveAndReturnDto() {
        CreateSpecimenRequest request = new CreateSpecimenRequest(
                "Bokoblin", "Eldin", 3, false
        );

        when(specimenMapper.toEntityCreate(request)).thenReturn(specimen);
        when(specimenRepository.save(specimen)).thenReturn(specimen);
        when(specimenMapper.toDto(specimen)).thenReturn(specimenResponse);

        SpecimenResponse result = specimenServices.createSpecimen(request);

        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo("Bokoblin");
        assertThat(result.getRegion()).isEqualTo("Eldin");
        assertThat(result.getDangerLevel()).isEqualTo(3);
        assertThat(result.getIsFriendly()).isFalse();

        verify(specimenMapper).toEntityCreate(request);
        verify(specimenRepository).save(specimen);
        verify(specimenMapper).toDto(specimen);
    }

    // ------------------------------------------------------------------ //
    //  getAllSpecimens
    // ------------------------------------------------------------------ //

    @Test
    @DisplayName("getAllSpecimens: debe retornar PageableResponse cuando hay especímenes")
    void getAllSpecimens_shouldReturnPageableResponse() {
        Page<Specimen> page = new PageImpl<>(List.of(specimen));

        when(specimenRepository.findAll(any(Pageable.class))).thenReturn(page);
        when(specimenMapper.toDto(specimen)).thenReturn(specimenResponse);

        PageableResponse<SpecimenResponse> result = specimenServices.getAllSpecimens(0, 10, "name", "asc");

        assertThat(result).isNotNull();
        assertThat(result.getTotalElements()).isEqualTo(1L);
        assertThat(result.getContent()).hasSize(1);

        verify(specimenRepository).findAll(any(Pageable.class));
        verify(specimenMapper).toDto(specimen);
    }

    @Test
    @DisplayName("getAllSpecimens: debe construir Sort descendente cuando sortOrder es 'desc'")
    void getAllSpecimens_shouldApplyDescendingSort() {
        Page<Specimen> page = new PageImpl<>(List.of(specimen));

        when(specimenRepository.findAll(any(Pageable.class))).thenReturn(page);
        when(specimenMapper.toDto(specimen)).thenReturn(specimenResponse);

        PageableResponse<SpecimenResponse> result = specimenServices.getAllSpecimens(0, 10, "dangerLevel", "desc");

        assertThat(result).isNotNull();
        verify(specimenRepository).findAll(any(Pageable.class));
    }

    // ------------------------------------------------------------------ //
    //  getSpecimenById
    // ------------------------------------------------------------------ //

    @Test
    @DisplayName("getSpecimenById: debe retornar el DTO cuando el espécimen existe")
    void getSpecimenById_shouldReturnDtoWhenFound() {
        when(specimenRepository.findById(specimenId)).thenReturn(Optional.of(specimen));
        when(specimenMapper.toDto(specimen)).thenReturn(specimenResponse);

        SpecimenResponse result = specimenServices.getSpecimenById(specimenId);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(specimenId);
        assertThat(result.getName()).isEqualTo("Bokoblin");

        verify(specimenRepository).findById(specimenId);
        verify(specimenMapper).toDto(specimen);
    }

    @Test
    @DisplayName("getSpecimenById: debe lanzar ResourceNotFoundException cuando no existe")
    void getSpecimenById_shouldThrowWhenNotFound() {
        when(specimenRepository.findById(specimenId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> specimenServices.getSpecimenById(specimenId))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Specimen not found in Hyrule Records");

        verify(specimenRepository).findById(specimenId);
        verifyNoInteractions(specimenMapper);
    }

    // ------------------------------------------------------------------ //
    //  updateSpecimen
    // ------------------------------------------------------------------ //

    @Test
    @DisplayName("updateSpecimen: debe actualizar y retornar el DTO cuando el espécimen existe")
    void updateSpecimen_shouldUpdateAndReturnDto() {
        UpdateSpecimenRequest updateRequest = new UpdateSpecimenRequest(
                "Blue Bokoblin", "Akkala", 5, false
        );

        Specimen updatedSpecimen = Specimen.builder()
                .id(specimenId)
                .name("Blue Bokoblin")
                .region("Akkala")
                .dangerLevel(5)
                .isFriendly(false)
                .build();

        SpecimenResponse updatedResponse = new SpecimenResponse(
                specimenId, "Blue Bokoblin", "Akkala", 5, false
        );

        // getSpecimenById interno necesita estas interacciones
        when(specimenRepository.findById(specimenId)).thenReturn(Optional.of(specimen));
        when(specimenMapper.toDto(specimen)).thenReturn(specimenResponse);

        when(specimenMapper.toEntityUpdate(updateRequest, specimenId)).thenReturn(updatedSpecimen);
        when(specimenRepository.save(updatedSpecimen)).thenReturn(updatedSpecimen);
        when(specimenMapper.toDto(updatedSpecimen)).thenReturn(updatedResponse);

        SpecimenResponse result = specimenServices.updateSpecimen(specimenId, updateRequest);

        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo("Blue Bokoblin");
        assertThat(result.getDangerLevel()).isEqualTo(5);

        verify(specimenRepository).findById(specimenId);
        verify(specimenMapper).toEntityUpdate(updateRequest, specimenId);
        verify(specimenRepository).save(updatedSpecimen);
    }

    @Test
    @DisplayName("updateSpecimen: debe lanzar ResourceNotFoundException si el espécimen no existe")
    void updateSpecimen_shouldThrowWhenSpecimenNotFound() {
        UpdateSpecimenRequest updateRequest = new UpdateSpecimenRequest(
                "Blue Bokoblin", "Akkala", 5, false
        );

        when(specimenRepository.findById(specimenId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> specimenServices.updateSpecimen(specimenId, updateRequest))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Specimen not found in Hyrule Records");

        verify(specimenRepository).findById(specimenId);
        verify(specimenRepository, never()).save(any());
    }



    @Test
    @DisplayName("deleteSpecimen: debe eliminar y retornar el DTO cuando el espécimen existe")
    void deleteSpecimen_shouldDeleteAndReturnDto() {
        when(specimenRepository.findById(specimenId)).thenReturn(Optional.of(specimen));
        when(specimenMapper.toDto(specimen)).thenReturn(specimenResponse);

        SpecimenResponse result = specimenServices.deleteSpecimen(specimenId);

        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo("Bokoblin");

        verify(specimenRepository).findById(specimenId);
        verify(specimenRepository).deleteById(specimenId);
    }

    @Test
    @DisplayName("deleteSpecimen: debe lanzar ResourceNotFoundException si el espécimen no existe")
    void deleteSpecimen_shouldThrowWhenSpecimenNotFound() {
        when(specimenRepository.findById(specimenId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> specimenServices.deleteSpecimen(specimenId))
                .isInstanceOf(ResourceNotFoundException.class);

        verify(specimenRepository, never()).deleteById(any());
    }
}
