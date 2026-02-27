package com.gustavobatista.production_control_api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gustavobatista.production_control_api.entity.Product;
import com.gustavobatista.production_control_api.entity.ProductMaterial;

public interface ProductMaterialRepository extends JpaRepository<ProductMaterial, Long> {

    boolean existsByProduct_IdAndRawMaterial_Id(Long productId, Long rawMaterialId);

    List<ProductMaterial> findByProduct(Product product);
}
