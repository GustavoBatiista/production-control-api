package com.gustavobatista.production_control_api.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gustavobatista.production_control_api.dto.ProducibleProductResponseDTO;
import com.gustavobatista.production_control_api.dto.ProductRequestDTO;
import com.gustavobatista.production_control_api.dto.ProductResponseDTO;
import com.gustavobatista.production_control_api.entity.Product;
import com.gustavobatista.production_control_api.entity.ProductMaterial;
import com.gustavobatista.production_control_api.exception.BusinessException;
import com.gustavobatista.production_control_api.exception.ResourceNotFoundException;
import com.gustavobatista.production_control_api.repository.ProductMaterialRepository;
import com.gustavobatista.production_control_api.repository.ProductRepository;

@Service
@Transactional
public class ProductService {

    private final Logger logger = LoggerFactory.getLogger(ProductService.class);

    private final ProductRepository productRepository;

    private final ProductMaterialRepository productMaterialRepository;

    public ProductService(ProductRepository productRepository, ProductMaterialRepository productMaterialRepository) {
        this.productRepository = productRepository;
        this.productMaterialRepository = productMaterialRepository;
    }

    public ProductResponseDTO createProduct(ProductRequestDTO dto) {

        logger.debug("Creating product with code: {}", dto.getCode());

        if (dto.getCode() == null || dto.getCode().isBlank()) {
            throw new BusinessException("Product code is required");
        }

        if (dto.getName() == null || dto.getName().isBlank()) {
            throw new BusinessException("Product name is required");
        }

        if (dto.getPrice() == null || dto.getPrice().compareTo(BigDecimal.ZERO) <= 0) {
            throw new BusinessException("Product price must be greater than zero");
        }

        if (productRepository.existsByCode(dto.getCode())) {
            throw new BusinessException("Product code already exists");
        }

        Product product = new Product(
                null,
                dto.getCode(),
                dto.getName(),
                dto.getPrice());

        Product saved = productRepository.save(product);

        logger.debug("Product created successfully with id: {}", saved.getId());

        return toResponseDTO(saved);
    }

    public List<ProductResponseDTO> getAllProducts() {
        logger.debug("Getting all products");
        List<Product> products = productRepository.findAll();
        List<ProductResponseDTO> productsResponse = products.stream()
                .map(product -> new ProductResponseDTO(product.getId(), product.getCode(), product.getName(),
                        product.getPrice()))
                .collect(Collectors.toList());
        logger.debug("Products found: {}", productsResponse.size());
        return productsResponse;
    }

    public ProductResponseDTO getProductById(Long id) {
        logger.debug("Getting product by id: {}", id);
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));
        return toResponseDTO(product);
    }

    @Transactional
    public ProductResponseDTO updateProduct(Long id, ProductRequestDTO dto) {
        logger.debug("Updating product by id: {}", id);
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));
        if (!product.getCode().equals(dto.getCode())
                && productRepository.existsByCode(dto.getCode())) {
            throw new BusinessException("Product code already exists");
        }
        product.setCode(dto.getCode());
        product.setName(dto.getName());
        product.setPrice(dto.getPrice());
        Product updated = productRepository.save(product);
        logger.debug("Product updated successfully id: {}", updated.getId());
        return toResponseDTO(updated);
    }

    public void deleteProduct(Long id) {
        logger.debug("Deleting product by id: {}", id);
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));
        productRepository.delete(product);
        logger.debug("Product deleted successfully id: {}", id);
    }

    public List<ProducibleProductResponseDTO> getProducibleProducts() {
        logger.debug("Getting all producible products");
        List<ProducibleProductResponseDTO> result = new ArrayList<>();

        for (Product product : productRepository.findAll()) {
            List<ProductMaterial> materials = productMaterialRepository.findByProduct(product);

            if (materials.isEmpty())
                continue;

            int maxQuantity = Integer.MAX_VALUE;

            for (ProductMaterial pm : materials) {
                float stock = pm.getRawMaterial().getStockQuantity();
                int required = pm.getQuantityRequired();
                int canProduce = (int) (stock / required);
                maxQuantity = Math.min(maxQuantity, canProduce);
            }

            if (maxQuantity > 0) {
                result.add(new ProducibleProductResponseDTO(
                        product.getId(), product.getCode(), product.getName(),
                        product.getPrice(), maxQuantity));
            }
        }

        result.sort((a, b) -> b.getPrice().compareTo(a.getPrice()));
        logger.debug("Producible products found: {}", result.size());
        return result;
    }

    private ProductResponseDTO toResponseDTO(Product product) {
        return new ProductResponseDTO(
                product.getId(),
                product.getCode(),
                product.getName(),
                product.getPrice());
    }
}
