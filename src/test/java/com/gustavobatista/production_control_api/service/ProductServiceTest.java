package com.gustavobatista.production_control_api.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.gustavobatista.production_control_api.dto.ProductRequestDTO;
import com.gustavobatista.production_control_api.dto.ProductResponseDTO;
import com.gustavobatista.production_control_api.entity.Product;
import com.gustavobatista.production_control_api.exception.BusinessException;
import com.gustavobatista.production_control_api.exception.ResourceNotFoundException;
import com.gustavobatista.production_control_api.repository.ProductMaterialRepository;
import com.gustavobatista.production_control_api.repository.ProductRepository;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ProductMaterialRepository productMaterialRepository;

    @InjectMocks
    private ProductService productService;

    private Product product;

    @BeforeEach
    void setUp() {
        product = new Product(1L, "P001", "Product 1", new BigDecimal("10.00"));
    }

    @Test
    void createProduct_shouldReturnCreatedProduct() {
        ProductRequestDTO dto = new ProductRequestDTO("P001", "Product 1", new BigDecimal("10.00"));
        when(productRepository.existsByCode("P001")).thenReturn(false);
        when(productRepository.save(any(Product.class))).thenReturn(product);

        ProductResponseDTO result = productService.createProduct(dto);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("P001", result.getCode());
        assertEquals("Product 1", result.getName());
        assertEquals(new BigDecimal("10.00"), result.getPrice());
        verify(productRepository).save(any(Product.class));
    }

    @Test
    void createProduct_shouldThrowWhenCodeIsBlank() {
        ProductRequestDTO dto = new ProductRequestDTO("", "Product 1", new BigDecimal("10.00"));

        assertThrows(BusinessException.class, () -> productService.createProduct(dto));
        verify(productRepository, never()).save(any());
    }

    @Test
    void createProduct_shouldThrowWhenCodeExists() {
        ProductRequestDTO dto = new ProductRequestDTO("P001", "Product 1", new BigDecimal("10.00"));
        when(productRepository.existsByCode("P001")).thenReturn(true);

        assertThrows(BusinessException.class, () -> productService.createProduct(dto));
        verify(productRepository, never()).save(any());
    }

    @Test
    void createProduct_shouldThrowWhenPriceIsZero() {
        ProductRequestDTO dto = new ProductRequestDTO("P001", "Product 1", BigDecimal.ZERO);
        when(productRepository.existsByCode("P001")).thenReturn(false);

        assertThrows(BusinessException.class, () -> productService.createProduct(dto));
        verify(productRepository, never()).save(any());
    }

    @Test
    void getAllProducts_shouldReturnList() {
        when(productRepository.findAll()).thenReturn(List.of(product));

        var result = productService.getAllProducts();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("P001", result.get(0).getCode());
        verify(productRepository).findAll();
    }

    @Test
    void getProductById_shouldReturnProduct() {
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        ProductResponseDTO result = productService.getProductById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Product 1", result.getName());
    }

    @Test
    void getProductById_shouldThrowWhenNotFound() {
        when(productRepository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> productService.getProductById(999L));
    }

    @Test
    void updateProduct_shouldReturnUpdatedProduct() {
        ProductRequestDTO dto = new ProductRequestDTO("P001", "Product Updated", new BigDecimal("20.00"));
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        when(productRepository.existsByCode("P001")).thenReturn(false);
        product.setName("Product Updated");
        product.setPrice(new BigDecimal("20.00"));
        when(productRepository.save(any(Product.class))).thenReturn(product);

        ProductResponseDTO result = productService.updateProduct(1L, dto);

        assertNotNull(result);
        assertEquals("Product Updated", result.getName());
        assertEquals(new BigDecimal("20.00"), result.getPrice());
        verify(productRepository).save(any(Product.class));
    }

    @Test
    void updateProduct_shouldThrowWhenNotFound() {
        ProductRequestDTO dto = new ProductRequestDTO("P001", "Product 1", new BigDecimal("10.00"));
        when(productRepository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> productService.updateProduct(999L, dto));
        verify(productRepository, never()).save(any());
    }

    @Test
    void deleteProduct_shouldDelete() {
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        productService.deleteProduct(1L);

        verify(productRepository).delete(product);
    }

    @Test
    void deleteProduct_shouldThrowWhenNotFound() {
        when(productRepository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> productService.deleteProduct(999L));
        verify(productRepository, never()).delete(any());
    }
}