package com.gustavobatista.production_control_api.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class ProductMaterialRequestDTO {

    @NotNull(message = "Product id is required")
    @Positive(message = "Product id must be greater than zero")
    private Long productId;
    @NotNull(message = "Raw material id is required")
    @Positive(message = "Raw material id must be greater than zero")
    private Long rawMaterialId;
    @NotNull(message = "Quantity required is required")
    @Positive(message = "Quantity required must be greater than zero")
    private Integer quantityRequired;

    public ProductMaterialRequestDTO() {
    }

    public ProductMaterialRequestDTO(Long productId, Long rawMaterialId, Integer quantityRequired) {
        this.productId = productId;
        this.rawMaterialId = rawMaterialId;
        this.quantityRequired = quantityRequired;
    }

    public Long getProductId() {
        return productId;
    }
    public void setProductId(Long productId) {
        this.productId = productId;
    }
    public Long getRawMaterialId() {
        return rawMaterialId;
    }
    public void setRawMaterialId(Long rawMaterialId) {
        this.rawMaterialId = rawMaterialId;
    }
    public Integer getQuantityRequired() {
        return quantityRequired;
    }
    public void setQuantityRequired(Integer quantityRequired) {
        this.quantityRequired = quantityRequired;
    }

    
}
