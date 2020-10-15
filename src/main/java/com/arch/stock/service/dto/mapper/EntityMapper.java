package com.arch.stock.service.dto.mapper;

import java.util.List;

public interface EntityMapper<D, E> {
    D toEntity(E dto);

    E toDto(D entity);

    List<D> toEntity(List<E> dtoList);

    List<E> toDto(List<D> entityList);
}
