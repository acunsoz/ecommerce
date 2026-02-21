package com.acunsoz.ecommerce_backend.service;

import com.acunsoz.ecommerce_backend.model.entity.Category;
import com.acunsoz.ecommerce_backend.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public Category saveCategory(Category category)
    {
        return categoryRepository.save(category);
    }


}
