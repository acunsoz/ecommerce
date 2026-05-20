package com.acunsoz.ecommerce_backend.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.Data;

import java.math.BigDecimal;

@Entity
@Data
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "cart_id")
    private Cart cart;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @Min(1)
    private Integer quantity;

    @Min(0)
    private BigDecimal price;


}
