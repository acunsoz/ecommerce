package com.acunsoz.ecommerce_backend.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class CartDto {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    private List<CartItemDto> items;
    private BigDecimal totalPrice;

}
