package com.acunsoz.ecommerce_backend.model.mapper;

import com.acunsoz.ecommerce_backend.model.dto.CartItemDto;
import com.acunsoz.ecommerce_backend.model.entity.CartItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ICartItemMapper {

    @Mapping(source = "product.id" ,target = "productId")
    @Mapping(source = "product.name",target = "productName")
    @Mapping(source = "product.imageUrl", target = "imageUrl")
    CartItemDto toDto(CartItem cartItem);

}
