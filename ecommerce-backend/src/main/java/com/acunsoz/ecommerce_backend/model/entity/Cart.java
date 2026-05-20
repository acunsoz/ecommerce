package com.acunsoz.ecommerce_backend.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.Data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // her kullanıcının sadece 1 sepeti olabilir onetoone
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    // Bir sepetin içinde birden fazla ürün (CartItem) olabilir
    // cascade = CascadeType.ALL -> Sepet silinirse içindeki ürünler de silinsin
    // orphanRemoval = true -> Ürün sepetten çıkarılırsa veritabanından da silinsin
    @OneToMany(mappedBy = "cart",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<CartItem> items = new ArrayList<>();

    @Min(0)
    private BigDecimal totalPrice = BigDecimal.ZERO;




}
