package com.acunsoz.ecommerce_backend.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name="products")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(name = "name",nullable = false)
    private String name;

    //@Column yazılmasa da değişken ismi ile aynı isimdeki sütunu arar.
    private String description;

    // para biriminde double yerine bu kullanılır hassasiyet kaybı olmasın diye
    @Min(0)
    @Column(precision = 10,scale = 2)
    private BigDecimal price;

    @Min(0)
    private Integer stock;

    @ManyToOne
    @JoinColumn(name = "category_id")
    @ToString.Exclude
    private Category category;

}
