package com.gustavobatista.production_control_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gustavobatista.production_control_api.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

    boolean existsByCode(String code);
}
