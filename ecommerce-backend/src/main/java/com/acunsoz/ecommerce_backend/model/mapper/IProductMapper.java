package com.acunsoz.ecommerce_backend.model.mapper;

import com.acunsoz.ecommerce_backend.model.dto.ProductDTO;
import com.acunsoz.ecommerce_backend.model.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface IProductMapper {

    // 1. Fırından Çıkan Yemeği (Entity) -> Müşteri Paketine (DTO) Çevir
    // Veritabanındaki "category" objesinin içindeki "id"yi alıp, DTO'daki "categoryId"ye yazar.
    @Mapping(source = "category.id",target = "categoryId")
    ProductDTO toDto(Product product);

    // 2. Müşteri Paketini (DTO) -> Pişirilecek Çiğ Ete (Entity) Çevir
    // Kategoriyi (category) görmezden gel diyoruz (ignore = true).
    // Çünkü hatırlarsan Adım 3'te, kategoriyi depodan (Repository) kendimiz bulup elimizle bağlıyorduk!
    @Mapping(target = "category",ignore = true)
    @Mapping(target = "id",ignore = true)
    Product toEntity(ProductDTO productDTO);

}
