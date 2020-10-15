package com.arch.stock.service.dto.mapper;

import com.arch.stock.domain.Supplier;
import com.arch.stock.service.dto.response.SupplierResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {})
public interface SupplierResponseMapper extends EntityMapper<Supplier, SupplierResponse> {

}
