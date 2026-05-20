package com.acunsoz.ecommerce_backend.model.mapper;

import com.acunsoz.ecommerce_backend.model.dto.OrderDto;
import com.acunsoz.ecommerce_backend.model.entity.Order;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {IOrderItemMapper.class})
public interface IOrderMapper {

    OrderDto toDto(Order order);

}
