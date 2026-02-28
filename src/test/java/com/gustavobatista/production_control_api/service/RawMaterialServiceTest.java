package com.gustavobatista.production_control_api.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.gustavobatista.production_control_api.dto.RawMaterialRequestDTO;
import com.gustavobatista.production_control_api.dto.RawMaterialResponseDTO;
import com.gustavobatista.production_control_api.entity.RawMaterial;
import com.gustavobatista.production_control_api.exception.ResourceNotFoundException;
import com.gustavobatista.production_control_api.repository.RawMaterialRepository;

@ExtendWith(MockitoExtension.class)
class RawMaterialServiceTest {

    @Mock
    private RawMaterialRepository rawMaterialRepository;

    @InjectMocks
    private RawMaterialService rawMaterialService;

    private RawMaterial rawMaterial;

    @BeforeEach
    void setUp() {
        rawMaterial = new RawMaterial(1L, "RM001", "Raw Material 1", 100.0f);
    }

    @Test
    void createRawMaterial_shouldReturnCreated() {
        RawMaterialRequestDTO dto = new RawMaterialRequestDTO("RM001", "Raw Material 1", 100.0f);
        when(rawMaterialRepository.save(any(RawMaterial.class))).thenReturn(rawMaterial);

        RawMaterialResponseDTO result = rawMaterialService.createRawMaterial(dto);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("RM001", result.getCode());
        assertEquals("Raw Material 1", result.getName());
        assertEquals(100.0f, result.getStockQuantity());
        verify(rawMaterialRepository).save(any(RawMaterial.class));
    }

    @Test
    void getAllRawMaterials_shouldReturnList() {
        when(rawMaterialRepository.findAll()).thenReturn(List.of(rawMaterial));

        var result = rawMaterialService.getAllRawMaterials();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("RM001", result.get(0).getCode());
        verify(rawMaterialRepository).findAll();
    }

    @Test
    void getRawMaterialById_shouldReturnRawMaterial() {
        when(rawMaterialRepository.findById(1L)).thenReturn(Optional.of(rawMaterial));

        RawMaterialResponseDTO result = rawMaterialService.getRawMaterialById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Raw Material 1", result.getName());
    }

    @Test
    void getRawMaterialById_shouldThrowWhenNotFound() {
        when(rawMaterialRepository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> rawMaterialService.getRawMaterialById(999L));
    }

    @Test
    void updateRawMaterial_shouldReturnUpdated() {
        RawMaterialRequestDTO dto = new RawMaterialRequestDTO("RM001", "Updated Name", 200.0f);
        when(rawMaterialRepository.findById(1L)).thenReturn(Optional.of(rawMaterial));
        rawMaterial.setName("Updated Name");
        rawMaterial.setStockQuantity(200.0f);
        when(rawMaterialRepository.save(any(RawMaterial.class))).thenReturn(rawMaterial);

        RawMaterialResponseDTO result = rawMaterialService.updateRawMaterial(1L, dto);

        assertNotNull(result);
        assertEquals("Updated Name", result.getName());
        assertEquals(200.0f, result.getStockQuantity());
        verify(rawMaterialRepository).save(any(RawMaterial.class));
    }

    @Test
    void updateRawMaterial_shouldThrowWhenNotFound() {
        RawMaterialRequestDTO dto = new RawMaterialRequestDTO("RM001", "Raw 1", 100.0f);
        when(rawMaterialRepository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> rawMaterialService.updateRawMaterial(999L, dto));
        verify(rawMaterialRepository, never()).save(any());
    }

    @Test
    void deleteRawMaterial_shouldDelete() {
        when(rawMaterialRepository.findById(1L)).thenReturn(Optional.of(rawMaterial));

        rawMaterialService.deleteRawMaterial(1L);

        verify(rawMaterialRepository).delete(rawMaterial);
    }

    @Test
    void deleteRawMaterial_shouldThrowWhenNotFound() {
        when(rawMaterialRepository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> rawMaterialService.deleteRawMaterial(999L));
        verify(rawMaterialRepository, never()).delete(any());
    }
}