package com.gustavobatista.production_control_api.dto;

public class RawMaterialResponseDTO {

    private Long id;
    private String code;
    private String name;
    private float stockQuantity;

    public RawMaterialResponseDTO(Long id, String code, String name, float stockQuantity) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.stockQuantity = stockQuantity;
    }

    public Long getId() {
        return id;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public float getStockQuantity() {
        return stockQuantity;
    }

}
