package com.acunsoz.ecommerce_backend.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductDTO {

    // BU SATIR ÇOK ÖNEMLİ:
    // access = READ_ONLY dedik.
    // Yani: "Kullanıcıya gösterirken ID'yi yaz, ama kullanıcıdan veri alırken bu alanı yoksay."
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    @NotBlank
    private String name;
    private String description;

    @NotNull
    @Min(0)
    private Integer stock;

    @NotNull
    @Min(0)
    private BigDecimal price;

    private Long categoryId;

    private String imageUrl;

}
