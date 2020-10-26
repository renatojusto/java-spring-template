package com.arch.stock.service;

import com.arch.stock.exception.NotFoundException;
import com.arch.stock.repository.SupplierRepository;
import com.arch.stock.service.dto.mapper.SupplierResponseMapper;
import com.arch.stock.service.dto.response.SupplierResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SupplierService {

    private SupplierRepository supplierRepository;
    private SupplierResponseMapper supplierResponseMapper;

    public SupplierService(SupplierRepository supplierRepository,
                           SupplierResponseMapper supplierResponseMapper) {
        this.supplierRepository = supplierRepository;
        this.supplierResponseMapper = supplierResponseMapper;
    }

    @Transactional
    public List<SupplierResponse> getAllSuppliers() {
        return supplierRepository.findAll()
            .stream()
            .map(supplierResponseMapper::toDto)
            .collect(Collectors.toList());
    }

    @Transactional
    public SupplierResponse getSupplier(Long id) {
        return supplierRepository.findById(id)
            .map(supplierResponseMapper::toDto)
            .orElseThrow(() -> wrapSupplierNotFoundException(id));
    }

    private RuntimeException wrapSupplierNotFoundException(Long id) {
        throw new NotFoundException(String.format("Supplier %d not found.", id));
    }
}
