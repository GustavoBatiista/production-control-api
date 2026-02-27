package com.gustavobatista.production_control_api.dto;

import java.math.BigDecimal;

public class ProducibleProductResponseDTO {
    private Long productId;
    private String productCode;
    private String productName;
    private BigDecimal price;
    private Integer maxQuantity;

    public ProducibleProductResponseDTO(Long productId, String productCode, String productName, BigDecimal price,
            Integer maxQuantity) {
        this.productId = productId;
        this.productCode = productCode;
        this.productName = productName;
        this.price = price;
        this.maxQuantity = maxQuantity;
    }

    public Long getProductId() {
        return productId;
    }

    public String getProductCode() {
        return productCode;
    }

    public String getProductName() {
        return productName;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public Integer getMaxQuantity() {
        return maxQuantity;
    }

}
