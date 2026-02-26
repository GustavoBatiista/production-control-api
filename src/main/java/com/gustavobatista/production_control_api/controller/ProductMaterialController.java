package com.gustavobatista.production_control_api.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gustavobatista.production_control_api.dto.ProductMaterialRequestDTO;
import com.gustavobatista.production_control_api.dto.ProductMaterialResponseDTO;
import com.gustavobatista.production_control_api.service.ProductMaterialService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/product-materials")
public class ProductMaterialController {

    private final ProductMaterialService productMaterialService;

    public ProductMaterialController(ProductMaterialService productMaterialService) {
        this.productMaterialService = productMaterialService;
    }

    @PostMapping
    public ResponseEntity<ProductMaterialResponseDTO> createProductMaterial(
            @Valid @RequestBody ProductMaterialRequestDTO dto) {
        ProductMaterialResponseDTO response = productMaterialService.createProductMaterial(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<ProductMaterialResponseDTO>> getAllProductMaterials() {
        List<ProductMaterialResponseDTO> response = productMaterialService.getAllProductMaterials();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductMaterialResponseDTO> getProductMaterialById(@PathVariable Long id) {
        ProductMaterialResponseDTO response = productMaterialService.getProductMaterialById(id);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductMaterialResponseDTO> updateProductMaterial(@PathVariable Long id,
            @Valid @RequestBody ProductMaterialRequestDTO dto) {
        ProductMaterialResponseDTO response = productMaterialService.updateProductMaterial(id, dto);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProductMaterial(@PathVariable Long id) {
        productMaterialService.deleteProductMaterial(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
