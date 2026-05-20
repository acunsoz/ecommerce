package com.acunsoz.ecommerce_backend.model.dto;

import lombok.Data;

@Data
public class AddToCartRequest {

    private Long productId;
    private Integer quantity;


}
