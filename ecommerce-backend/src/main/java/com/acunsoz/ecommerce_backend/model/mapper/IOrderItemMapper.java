package com.acunsoz.ecommerce_backend.model.mapper;

import com.acunsoz.ecommerce_backend.model.dto.OrderItemDto;
import com.acunsoz.ecommerce_backend.model.entity.OrderItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface IOrderItemMapper {

    @Mapping(source = "product.id",target = "productId")
    @Mapping(source = "product.name",target = "productName")
    OrderItemDto toDto(OrderItem orderItem);

}
