package com.gustavobatista.production_control_api.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.gustavobatista.production_control_api.dto.ProductMaterialRequestDTO;
import com.gustavobatista.production_control_api.dto.ProductMaterialResponseDTO;
import com.gustavobatista.production_control_api.entity.ProductMaterial;
import com.gustavobatista.production_control_api.exception.ResourceNotFoundException;
import com.gustavobatista.production_control_api.repository.ProductMaterialRepository;
import com.gustavobatista.production_control_api.repository.ProductRepository;
import com.gustavobatista.production_control_api.repository.RawMaterialRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class ProductMaterialService {

    private final Logger logger = LoggerFactory.getLogger(ProductMaterialService.class);

    private final ProductRepository productRepository;
    private final RawMaterialRepository rawMaterialRepository;
    private final ProductMaterialRepository productMaterialRepository;

    public ProductMaterialService(
            ProductRepository productRepository,
            RawMaterialRepository rawMaterialRepository,
            ProductMaterialRepository productMaterialRepository) {

        this.productRepository = productRepository;
        this.rawMaterialRepository = rawMaterialRepository;
        this.productMaterialRepository = productMaterialRepository;
    }

    public ProductMaterialResponseDTO createProductMaterial(ProductMaterialRequestDTO dto) {

        logger.debug("Creating product material: {}", dto);

        var product = productRepository.findById(dto.getProductId())
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        var rawMaterial = rawMaterialRepository.findById(dto.getRawMaterialId())
                .orElseThrow(() -> new ResourceNotFoundException("Raw material not found"));

        ProductMaterial entity = new ProductMaterial(
                null,
                product,
                rawMaterial,
                dto.getQuantityRequired()
        );

        return toResponseDTO(productMaterialRepository.save(entity));
    }

    public List<ProductMaterialResponseDTO> getAllProductMaterials() {
        return productMaterialRepository.findAll()
                .stream()
                .map(this::toResponseDTO)
                .toList();
    }

    public ProductMaterialResponseDTO getProductMaterialById(Long id) {

        var entity = productMaterialRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product material not found"));

        return toResponseDTO(entity);
    }

    public ProductMaterialResponseDTO updateProductMaterial(Long id, ProductMaterialRequestDTO dto) {

        var entity = productMaterialRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product material not found"));

        entity.setQuantityRequired(dto.getQuantityRequired());

        return toResponseDTO(entity);
    }

    public void deleteProductMaterial(Long id) {

        var entity = productMaterialRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product material not found"));

        productMaterialRepository.delete(entity);
    }

    private ProductMaterialResponseDTO toResponseDTO(ProductMaterial pm) {
        return new ProductMaterialResponseDTO(
                pm.getId(),
                pm.getProduct().getId(),
                pm.getRawMaterial().getId(),
                pm.getQuantityRequired()
        );
    }
}