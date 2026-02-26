package com.gustavobatista.production_control_api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class RawMaterialRequestDTO {

    @NotBlank(message = "Code is required")
    private String code;
    @NotBlank(message = "Name is required")
    private String name;
    @NotNull(message = "Stock quantity is required")
    @Positive(message = "Stock quantity must be greater than zero")
    private float stockQuantity;

    public RawMaterialRequestDTO() {
    }

    public RawMaterialRequestDTO(String code, String name, float stockQuantity) {
        this.code = code;
        this.name = name;
        this.stockQuantity = stockQuantity;
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

    public void setCode(String code) {
        this.code = code;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setStockQuantity(float stockQuantity) {
        this.stockQuantity = stockQuantity;
    }


}