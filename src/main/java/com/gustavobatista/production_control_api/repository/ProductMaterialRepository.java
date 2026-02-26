package com.gustavobatista.production_control_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gustavobatista.production_control_api.entity.ProductMaterial;

public interface ProductMaterialRepository extends JpaRepository<ProductMaterial, Long> {

    boolean existsByProduct_IdAndRawMaterial_Id(Long productId, Long rawMaterialId);

    
}
