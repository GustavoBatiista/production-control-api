package com.gustavobatista.production_control_api.entity;

import org.springframework.data.annotation.Id;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(name = "product_materials", uniqueConstraints = {
        @UniqueConstraint(columnNames = { "product_id", "raw_material_id" })
})
public class ProductMaterial {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @ManyToOne
    @JoinColumn(name = "raw_material_id", nullable = false)
    private RawMaterial rawMaterial;

    @Column(nullable = false)
    private Integer quantityRequired;

    public ProductMaterial(Long id, Product product, RawMaterial rawMaterial, Integer quantityRequired) {
        this.id = id;
        this.product = product;
        this.rawMaterial = rawMaterial;
        this.quantityRequired = quantityRequired;
    }

    public Long getId() {
        return id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public RawMaterial getRawMaterial() {
        return rawMaterial;
    }

    public void setRawMaterial(RawMaterial rawMaterial) {
        this.rawMaterial = rawMaterial;
    }

    public Integer getQuantityRequired() {
        return quantityRequired;
    }

    public void setQuantityRequired(Integer quantityRequired) {
        this.quantityRequired = quantityRequired;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((product == null) ? 0 : product.hashCode());
        result = prime * result + ((rawMaterial == null) ? 0 : rawMaterial.hashCode());
        result = prime * result + ((quantityRequired == null) ? 0 : quantityRequired.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        ProductMaterial other = (ProductMaterial) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (product == null) {
            if (other.product != null)
                return false;
        } else if (!product.equals(other.product))
            return false;
        if (rawMaterial == null) {
            if (other.rawMaterial != null)
                return false;
        } else if (!rawMaterial.equals(other.rawMaterial))
            return false;
        if (quantityRequired == null) {
            if (other.quantityRequired != null)
                return false;
        } else if (!quantityRequired.equals(other.quantityRequired))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "ProductMaterial [id=" + id + ", product=" + product + ", rawMaterial=" + rawMaterial
                + ", quantityRequired=" + quantityRequired + "]";
    }

}