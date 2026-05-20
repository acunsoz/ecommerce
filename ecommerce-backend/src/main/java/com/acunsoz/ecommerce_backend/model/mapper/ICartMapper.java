package com.acunsoz.ecommerce_backend.model.mapper;

import com.acunsoz.ecommerce_backend.model.dto.CartDto;
import com.acunsoz.ecommerce_backend.model.entity.Cart;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {ICartItemMapper.class})
public interface ICartMapper {

    CartDto toDto(Cart cart);

}
