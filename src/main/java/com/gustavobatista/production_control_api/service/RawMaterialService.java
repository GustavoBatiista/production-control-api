package com.gustavobatista.production_control_api.service;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gustavobatista.production_control_api.dto.RawMaterialRequestDTO;
import com.gustavobatista.production_control_api.dto.RawMaterialResponseDTO;
import com.gustavobatista.production_control_api.entity.RawMaterial;
import com.gustavobatista.production_control_api.exception.ResourceNotFoundException;
import com.gustavobatista.production_control_api.repository.RawMaterialRepository;

@Service
@Transactional
public class RawMaterialService {

    private final Logger logger = LoggerFactory.getLogger(RawMaterialService.class);

    private final RawMaterialRepository rawMaterialRepository;

    public RawMaterialService(RawMaterialRepository rawMaterialRepository) {
        this.rawMaterialRepository = rawMaterialRepository;
    }

    public RawMaterialResponseDTO createRawMaterial(RawMaterialRequestDTO dto) {
        logger.debug("Creating raw material: {}", dto);
        RawMaterial rawMaterial = new RawMaterial(null,dto.getCode(), dto.getName(), dto.getStockQuantity());
        RawMaterial saved = rawMaterialRepository.save(rawMaterial);
        return toResponseDTO(saved);
    }

    public List<RawMaterialResponseDTO> getAllRawMaterials() {
        logger.debug("Getting all raw materials");
        List<RawMaterial> rawMaterials = rawMaterialRepository.findAll();
        return rawMaterials.stream().map(this::toResponseDTO).collect(Collectors.toList());
    }

    public RawMaterialResponseDTO getRawMaterialById(Long id) {
        logger.debug("Getting raw material by id: {}", id);
        RawMaterial rawMaterial = rawMaterialRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Raw material not found"));

        return toResponseDTO(rawMaterial);
    }

    @Transactional
    public RawMaterialResponseDTO updateRawMaterial(Long id, RawMaterialRequestDTO dto) {
        logger.debug("Updating raw material by id: {}", id);
        RawMaterial rawMaterial = rawMaterialRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Raw material not found"));
        rawMaterial.setCode(dto.getCode());
        rawMaterial.setName(dto.getName());
        rawMaterial.setStockQuantity(dto.getStockQuantity());
        RawMaterial saved = rawMaterialRepository.save(rawMaterial);
        return toResponseDTO(saved);

    }

    public void deleteRawMaterial(Long id) {
        logger.debug("Deleting raw material by id: {}", id);
        RawMaterial rawMaterial = rawMaterialRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Raw material not found"));
        rawMaterialRepository.delete(rawMaterial);
        logger.debug("Raw material deleted successfully id: {}", id);
    }

    private RawMaterialResponseDTO toResponseDTO(RawMaterial rawMaterial) {
        return new RawMaterialResponseDTO(rawMaterial.getId(), rawMaterial.getCode(), rawMaterial.getName(),
                rawMaterial.getStockQuantity());
    }

}
