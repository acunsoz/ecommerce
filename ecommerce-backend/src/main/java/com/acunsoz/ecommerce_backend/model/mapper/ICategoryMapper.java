package com.acunsoz.ecommerce_backend.model.mapper;

import com.acunsoz.ecommerce_backend.model.dto.CategoryDTO;
import com.acunsoz.ecommerce_backend.model.entity.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ICategoryMapper {
    CategoryDTO toDto(Category category);

    @Mapping(target = "id",ignore = true)
    Category toEntity(CategoryDTO categoryDTO);
}
