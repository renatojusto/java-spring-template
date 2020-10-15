package com.arch.stock.resource;

import com.arch.stock.service.ProductService;
import com.arch.stock.service.dto.request.ProductCreateRequest;
import com.arch.stock.service.dto.request.ProductDecreaseRequest;
import com.arch.stock.service.dto.request.ProductIncreaseRequest;
import com.arch.stock.service.dto.request.ProductUpdateRequest;
import com.arch.stock.service.dto.response.ProductResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/v1/products")
public class ProductResource {

    private static final Logger log = LoggerFactory.getLogger(ProductResource.class);
    private static final String PATH = "/v1/products";

    private ProductService productService;

    public ProductResource(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public List<ProductResponse> getAllProducts() {
        log.debug("Chamada Rest getAllProducts");
        List<ProductResponse> products = productService.getAllProducts();
        for (ProductResponse product : products) {
            addHateoasLink(product);
        }
        return products;
    }

    @GetMapping("/{id}")
    public ProductResponse getProduct(@PathVariable Long id) {
        log.debug("Chamada Rest getProduct {}", id);
        ProductResponse product = productService.getProduct(id);
        addHateoasLink(product);
        return product;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<ProductResponse> createProduct(@Valid @RequestBody ProductCreateRequest productCreateRequest,
                                                         UriComponentsBuilder uriComponentsBuilder) {
        log.debug("Chamada Rest createProduct: {}", productCreateRequest.getName());
        ProductResponse product = productService.createProduct(productCreateRequest);
        addHateoasLink(product);
        URI location = uriComponentsBuilder.path(PATH + "/{id}").buildAndExpand(product.getId()).toUri();
        return ResponseEntity.created(location).body(product);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ProductResponse updateProduct(@PathVariable Long id, @Valid @RequestBody ProductUpdateRequest productUpdateRequest) {
        log.debug("Chamada Rest updateProduct: {} {}", id, productUpdateRequest.getName());
        ProductResponse productResponse = productService.updateProduct(id, productUpdateRequest);
        addHateoasLink(productResponse);
        return productResponse;
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProduct(@PathVariable Long id) {
        log.debug("Chamada Rest deleteProduct: {}", id);
        productService.deleteProduct(id);
    }

    @PostMapping("/{id}/increase")
    @ResponseStatus(HttpStatus.OK)
    public ProductResponse increaseProduct(@PathVariable Long id, @Valid @RequestBody ProductIncreaseRequest productIncreaseRequest) {
        log.debug("Chamada Rest increaseProduct: {}", id);
        ProductResponse productResponse = productService.increaseProduct(id, productIncreaseRequest.getQuantity());
        addHateoasLink(productResponse);
        return productResponse;
    }

    @PostMapping("/{id}/decrease")
    @ResponseStatus(HttpStatus.OK)
    public ProductResponse decreaseProduct(@PathVariable Long id, @Valid @RequestBody ProductDecreaseRequest productDecreaseRequest) {
        log.debug("Chamada Rest decreaseProduct: {}", id);
        ProductResponse productResponse = productService.decreaseProduct(id, productDecreaseRequest.getQuantity());
        addHateoasLink(productResponse);
        return productResponse;
    }

    private void addHateoasLink(ProductResponse product) {
        product.add(linkTo(methodOn(ProductResource.class).getProduct(product.getId())).withSelfRel());
        if (product.getSupplier() != null) {
            product.getSupplier().add(WebMvcLinkBuilder.linkTo(methodOn(SupplierResource.class).getSupplier(product.getSupplier().getId())).withSelfRel());
        }
    }
}
