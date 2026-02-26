package com.gustavobatista.production_control_api.dto;

import java.math.BigDecimal;

public class ProductResponseDTO {

    private Long id;
    private String code;
    private String name;
    private BigDecimal price;

    public ProductResponseDTO(Long id, String code, String name, BigDecimal price) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.price = price;
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

    public BigDecimal getPrice() {
        return price;
    }

}
