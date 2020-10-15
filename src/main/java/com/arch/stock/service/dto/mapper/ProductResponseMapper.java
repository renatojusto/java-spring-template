package com.arch.stock.service.dto.mapper;

import com.arch.stock.domain.Product;
import com.arch.stock.service.dto.response.ProductResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {SupplierResponseMapper.class})
public interface ProductResponseMapper extends EntityMapper<Product, ProductResponse> {

    @Mapping(source = "supplier", target = "supplier")
    Product toEntity(ProductResponse dto);

    @Mapping(source = "supplier", target = "supplier")
    ProductResponse toDto(Product entity);

}
