package com.gustavobatista.production_control_api.dto;

public class ProductMaterialResponseDTO {


    private Long id;
    private Long productId;
    private Long rawMaterialId;
    private Integer quantityRequired;

    public ProductMaterialResponseDTO(Long id, Long productId, Long rawMaterialId, Integer quantityRequired) {
        this.id = id;
        this.productId = productId;
        this.rawMaterialId = rawMaterialId;
        this.quantityRequired = quantityRequired;
    }
    public Long getId() {
        return id;
    }
    public Long getProductId() {
        return productId;
    }
    public Long getRawMaterialId() {
        return rawMaterialId;
    }
    public Integer getQuantityRequired() {
        return quantityRequired;
    }
}
