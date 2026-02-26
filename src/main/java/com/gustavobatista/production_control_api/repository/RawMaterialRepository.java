package com.gustavobatista.production_control_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gustavobatista.production_control_api.entity.RawMaterial;

public interface RawMaterialRepository extends JpaRepository<RawMaterial, Long> {

    boolean existsByCode(String code);
    
}
