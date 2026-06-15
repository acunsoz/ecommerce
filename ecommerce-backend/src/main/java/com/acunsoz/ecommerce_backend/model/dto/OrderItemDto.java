package com.acunsoz.ecommerce_backend.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;


@Data
public class OrderItemDto {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    private Long productId;
    private String productName;
    private Integer quantity;
    private BigDecimal price;
    private String imageUrl;
}
