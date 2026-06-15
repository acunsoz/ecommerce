package com.acunsoz.ecommerce_backend.model.mapper;

import com.acunsoz.ecommerce_backend.model.dto.OrderItemDto;
import com.acunsoz.ecommerce_backend.model.entity.OrderItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface IOrderItemMapper {

    @Mapping(source = "product.id",target = "productId")
    @Mapping(source = "product.name",target = "productName")
    @Mapping(source = "product.imageUrl", target = "imageUrl")
    OrderItemDto toDto(OrderItem orderItem);

}
