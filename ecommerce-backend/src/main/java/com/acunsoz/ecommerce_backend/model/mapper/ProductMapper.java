package com.acunsoz.ecommerce_backend.model.mapper;

import com.acunsoz.ecommerce_backend.model.dto.ProductDTO;
import com.acunsoz.ecommerce_backend.model.entity.Category;
import com.acunsoz.ecommerce_backend.model.entity.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

    //Dto yu entity e cevirme
    public Product toEntity(ProductDTO productDTO)
    {
        Product product = new Product();
        product.setName(productDTO.getName());
        product.setDescription(productDTO.getDescription());
        product.setStock(productDTO.getStock());
        product.setPrice(productDTO.getPrice());
        if (productDTO.getCategoryId() != null)
        {
            Category category = new Category();
            category.setId(productDTO.getCategoryId());
            product.setCategory(category);
        }

        return product;
    }

    //2. entity den dto ya cevirme
    public ProductDTO toDTO(Product entity) {
        ProductDTO replyProductDTO = new ProductDTO();
        replyProductDTO.setId(entity.getId());
        replyProductDTO.setName(entity.getName());
        replyProductDTO.setDescription(entity.getDescription());
        replyProductDTO.setStock(entity.getStock());
        replyProductDTO.setPrice(entity.getPrice());
        replyProductDTO.setCategoryId(entity.getCategory().getId());

        return replyProductDTO;
    }
}
