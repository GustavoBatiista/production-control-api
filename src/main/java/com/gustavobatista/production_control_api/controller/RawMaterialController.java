package com.gustavobatista.production_control_api.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gustavobatista.production_control_api.dto.RawMaterialRequestDTO;
import com.gustavobatista.production_control_api.dto.RawMaterialResponseDTO;
import com.gustavobatista.production_control_api.service.RawMaterialService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/raw-materials")
public class RawMaterialController {

    private final RawMaterialService rawMaterialService;

    public RawMaterialController(RawMaterialService rawMaterialService) {
        this.rawMaterialService = rawMaterialService;
    }

    @PostMapping
    public ResponseEntity<RawMaterialResponseDTO> createRawMaterial( @Valid @RequestBody RawMaterialRequestDTO dto) {
        RawMaterialResponseDTO response = rawMaterialService.createRawMaterial(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<RawMaterialResponseDTO>> getAllRawMaterials() {
        List<RawMaterialResponseDTO> response = rawMaterialService.getAllRawMaterials();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RawMaterialResponseDTO> getRawMaterialById(@PathVariable Long id) {
        RawMaterialResponseDTO response = rawMaterialService.getRawMaterialById(id);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RawMaterialResponseDTO> updateRawMaterial(@PathVariable Long id,
            @Valid @RequestBody RawMaterialRequestDTO dto) {
        RawMaterialResponseDTO response = rawMaterialService.updateRawMaterial(id, dto);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRawMaterial(@PathVariable Long id) {
        rawMaterialService.deleteRawMaterial(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
