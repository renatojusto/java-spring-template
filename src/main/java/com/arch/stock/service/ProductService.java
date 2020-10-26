package com.arch.stock.service;

import com.arch.stock.domain.Product;
import com.arch.stock.domain.Supplier;
import com.arch.stock.exception.NotFoundException;
import com.arch.stock.repository.ProductRepository;
import com.arch.stock.repository.SupplierRepository;
import com.arch.stock.service.dto.mapper.ProductResponseMapper;
import com.arch.stock.service.dto.request.ProductCreateRequest;
import com.arch.stock.service.dto.request.ProductUpdateRequest;
import com.arch.stock.service.dto.response.ProductResponse;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private ProductRepository productRepository;
    private SupplierRepository supplierRepository;
    private ProductResponseMapper productResponseMapper;

    public ProductService(ProductRepository productRepository,
                          SupplierRepository supplierRepository,
                          ProductResponseMapper productResponseMapper) {
        this.productRepository = productRepository;
        this.supplierRepository = supplierRepository;
        this.productResponseMapper = productResponseMapper;
    }

    @Transactional
    public List<ProductResponse> getAllProducts() {
        return productRepository.findAll()
            .stream()
            .map(productResponseMapper::toDto)
            .collect(Collectors.toList());
    }

    @Transactional
    public ProductResponse getProduct(Long id) {
        return productRepository.findById(id)
            .map(productResponseMapper::toDto)
            .orElseThrow(() -> wrapNotFoundException(id));
    }

    @Transactional
    public ProductResponse createProduct(ProductCreateRequest productCreateRequest) {
        Supplier supplier = getSupplierOrThrowException(productCreateRequest.getSupplierId());

        Product product = new Product();
        product.setName(productCreateRequest.getName());
        product.setUnitPrice(productCreateRequest.getUnitPrice());
        product.setQuantityStock(productCreateRequest.getInitialQuantityStock());
        product.setDateCreation(new java.util.Date());
        product.setSupplier(supplier);
        Product saved = productRepository.save(product);
        return productResponseMapper.toDto(saved);
    }

    @Transactional
    public ProductResponse updateProduct(Long id, ProductUpdateRequest productUpdateRequest) {
        Supplier supplier = getSupplierOrThrowException(productUpdateRequest.getSupplierId());

        Product product = getProductOrThrowNotFoundException(id);
        product.setName(productUpdateRequest.getName());
        product.setUnitPrice(productUpdateRequest.getUnitPrice());
        product.setSupplier(supplier);
        product.setDateLastUpdate(new java.util.Date());
        Product saved = productRepository.save(product);
        return productResponseMapper.toDto(saved);
    }

    private Supplier getSupplierOrThrowException(Long supplierId) {
        return supplierRepository.findById(supplierId)
            .orElseThrow(() -> wrapSupplierNotFoundException(supplierId));
    }

    private RuntimeException wrapSupplierNotFoundException(Long id) {
        throw new NotFoundException(String.format("Supplier %d not found.", id));
    }

    @Transactional
    public void deleteProduct(Long id) {
        try {
            productRepository.deleteById(id);
        } catch (EmptyResultDataAccessException exception) {
            wrapNotFoundException(id);
        }
    }

    @Transactional
    public ProductResponse increaseProduct(Long id, BigDecimal quantity) {
        Product product = getProductOrThrowNotFoundException(id);
        product.increaseQuantityStock(quantity);
        product.setDateLastUpdate(new java.util.Date());
        Product saved = productRepository.save(product);
        return productResponseMapper.toDto(saved);
    }

    @Transactional
    public ProductResponse decreaseProduct(Long id, BigDecimal quantity) {
        Product product = getProductOrThrowNotFoundException(id);
        product.decreaseQuantityStock(quantity);
        product.setDateLastUpdate(new java.util.Date());
        Product saved = productRepository.save(product);
        return productResponseMapper.toDto(saved);
    }

    private Product getProductOrThrowNotFoundException(Long id) {
        return productRepository.findById(id).orElseThrow(() -> wrapNotFoundException(id));
    }

    private RuntimeException wrapNotFoundException(Long id) {
        throw new NotFoundException(String.format("Product %d not found.", id));
    }
}
