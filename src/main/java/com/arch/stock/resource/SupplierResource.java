package com.arch.stock.resource;

import com.arch.stock.service.SupplierService;
import com.arch.stock.service.dto.response.SupplierResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/v1/suppliers")
public class SupplierResource {

    private static final Logger log = LoggerFactory.getLogger(SupplierResource.class);

    private SupplierService supplierService;

    public SupplierResource(SupplierService supplierService) {
        this.supplierService = supplierService;
    }

    @GetMapping
    public List<SupplierResponse> getAllSuppliers() {
        log.debug("Chamada Rest getAllSuppliers");
        List<SupplierResponse> allSuppliers = supplierService.getAllSuppliers();
        for (SupplierResponse supplier : allSuppliers) {
            addHateoasLink(supplier);
        }
        return allSuppliers;
    }

    @GetMapping("/{id}")
    public SupplierResponse getSupplier(@PathVariable Long id) {
        log.debug("Chamada Rest getSupplier {}", id);
        SupplierResponse supplier = supplierService.getSupplier(id);
        addHateoasLink(supplier);
        return supplier;
    }

    private void addHateoasLink(SupplierResponse supplier) {
        supplier.add(linkTo(methodOn(SupplierResource.class).getSupplier(supplier.getId())).withSelfRel());
    }
}
