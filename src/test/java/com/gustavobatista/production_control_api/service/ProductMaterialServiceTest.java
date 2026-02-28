package com.gustavobatista.production_control_api.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.gustavobatista.production_control_api.dto.ProductMaterialRequestDTO;
import com.gustavobatista.production_control_api.dto.ProductMaterialResponseDTO;
import com.gustavobatista.production_control_api.entity.Product;
import com.gustavobatista.production_control_api.entity.ProductMaterial;
import com.gustavobatista.production_control_api.entity.RawMaterial;
import com.gustavobatista.production_control_api.exception.ResourceNotFoundException;
import com.gustavobatista.production_control_api.repository.ProductMaterialRepository;
import com.gustavobatista.production_control_api.repository.ProductRepository;
import com.gustavobatista.production_control_api.repository.RawMaterialRepository;

@ExtendWith(MockitoExtension.class)
class ProductMaterialServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private RawMaterialRepository rawMaterialRepository;

    @Mock
    private ProductMaterialRepository productMaterialRepository;

    @InjectMocks
    private ProductMaterialService productMaterialService;

    private Product product;
    private RawMaterial rawMaterial;
    private ProductMaterial productMaterial;

    @BeforeEach
    void setUp() {
        product = new Product(1L, "P001", "Product 1", new BigDecimal("10.00"));
        rawMaterial = new RawMaterial(1L, "RM001", "Raw Material 1", 100.0f);
        productMaterial = new ProductMaterial(1L, product, rawMaterial, 10);
    }

    @Test
    void createProductMaterial_shouldReturnCreated() {
        ProductMaterialRequestDTO dto = new ProductMaterialRequestDTO(1L, 1L, 10);
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        when(rawMaterialRepository.findById(1L)).thenReturn(Optional.of(rawMaterial));
        when(productMaterialRepository.save(any(ProductMaterial.class))).thenReturn(productMaterial);

        ProductMaterialResponseDTO result = productMaterialService.createProductMaterial(dto);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals(1L, result.getProductId());
        assertEquals(1L, result.getRawMaterialId());
        assertEquals(10, result.getQuantityRequired());
        verify(productMaterialRepository).save(any(ProductMaterial.class));
    }

    @Test
    void createProductMaterial_shouldThrowWhenProductNotFound() {
        ProductMaterialRequestDTO dto = new ProductMaterialRequestDTO(999L, 1L, 10);
        when(productRepository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> productMaterialService.createProductMaterial(dto));
        verify(productMaterialRepository, never()).save(any());
    }

    @Test
    void createProductMaterial_shouldThrowWhenRawMaterialNotFound() {
        ProductMaterialRequestDTO dto = new ProductMaterialRequestDTO(1L, 999L, 10);
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        when(rawMaterialRepository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> productMaterialService.createProductMaterial(dto));
        verify(productMaterialRepository, never()).save(any());
    }

    @Test
    void getAllProductMaterials_shouldReturnList() {
        when(productMaterialRepository.findAll()).thenReturn(List.of(productMaterial));

        var result = productMaterialService.getAllProductMaterials();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(1L, result.get(0).getId());
        verify(productMaterialRepository).findAll();
    }

    @Test
    void getProductMaterialById_shouldReturnProductMaterial() {
        when(productMaterialRepository.findById(1L)).thenReturn(Optional.of(productMaterial));

        ProductMaterialResponseDTO result = productMaterialService.getProductMaterialById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals(10, result.getQuantityRequired());
    }

    @Test
    void getProductMaterialById_shouldThrowWhenNotFound() {
        when(productMaterialRepository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> productMaterialService.getProductMaterialById(999L));
    }

    @Test
    void updateProductMaterial_shouldReturnUpdated() {
        ProductMaterialRequestDTO dto = new ProductMaterialRequestDTO(1L, 1L, 20);
        when(productMaterialRepository.findById(1L)).thenReturn(Optional.of(productMaterial));
        productMaterial.setQuantityRequired(20);
        when(productMaterialRepository.save(any(ProductMaterial.class))).thenReturn(productMaterial);

        ProductMaterialResponseDTO result = productMaterialService.updateProductMaterial(1L, dto);

        assertNotNull(result);
        assertEquals(20, result.getQuantityRequired());
        verify(productMaterialRepository).save(any(ProductMaterial.class));
    }

    @Test
    void updateProductMaterial_shouldThrowWhenNotFound() {
        ProductMaterialRequestDTO dto = new ProductMaterialRequestDTO(1L, 1L, 20);
        when(productMaterialRepository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> productMaterialService.updateProductMaterial(999L, dto));
        verify(productMaterialRepository, never()).save(any());
    }

    @Test
    void deleteProductMaterial_shouldDelete() {
        when(productMaterialRepository.findById(1L)).thenReturn(Optional.of(productMaterial));

        productMaterialService.deleteProductMaterial(1L);

        verify(productMaterialRepository).delete(productMaterial);
    }

    @Test
    void deleteProductMaterial_shouldThrowWhenNotFound() {
        when(productMaterialRepository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> productMaterialService.deleteProductMaterial(999L));
        verify(productMaterialRepository, never()).delete(any());
    }
}