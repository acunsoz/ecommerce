package com.acunsoz.ecommerce_backend.model.dto;

import com.acunsoz.ecommerce_backend.model.enums.OrderStatus;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class OrderDto {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    private List<OrderItemDto> items;
    private BigDecimal totalAmount;
    private OrderStatus status;
    private LocalDateTime orderDate;

}
