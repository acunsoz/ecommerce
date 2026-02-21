package com.acunsoz.ecommerce_backend.bootstrap;

import com.acunsoz.ecommerce_backend.model.entity.Category;
import com.acunsoz.ecommerce_backend.model.entity.Product;
import com.acunsoz.ecommerce_backend.repository.CategoryRepository;
import com.acunsoz.ecommerce_backend.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {

    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;

    @Override
    public void run(String... args) throws Exception {

        if (categoryRepository.findByName("Elektronik").isEmpty()) {
            Category k1 = Category.builder()
                    .name("Elektronik")
                    .build();
            categoryRepository.save(k1);

            Product p1 = Product.builder()
                    .name("Laptop Mini")
                    .description("tasimasi kolay ve hizli")
                    .price(BigDecimal.valueOf(30500))
                    .stock(10)
                    .category(k1)
                    .build();
            productRepository.save(p1);

            System.out.println(k1);
        }

    }
}
