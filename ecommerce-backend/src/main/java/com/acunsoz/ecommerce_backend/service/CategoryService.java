package com.acunsoz.ecommerce_backend.service;

import com.acunsoz.ecommerce_backend.model.dto.CategoryDTO;
import com.acunsoz.ecommerce_backend.model.entity.Category;
import com.acunsoz.ecommerce_backend.model.mapper.ICategoryMapper;
import com.acunsoz.ecommerce_backend.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final ICategoryMapper categoryMapper;

    public Category saveCategory(Category category)
    {
        return categoryRepository.save(category);
    }

    public List<CategoryDTO> getAllCategories() {
        return categoryRepository.findAll()
                .stream()
                .map(categoryMapper::toDto)
                .collect(Collectors.toList());
    }

}
