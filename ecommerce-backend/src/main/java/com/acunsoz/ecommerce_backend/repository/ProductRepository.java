package com.acunsoz.ecommerce_backend.repository;

import com.acunsoz.ecommerce_backend.model.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long>
{
    List<Product> findByNameStartingWithIgnoreCase(String prefix);

    // YENİ EKLENEN SORGU: Belirli bir adminId'ye ait ürünleri sayfalayarak getirir
    Page<Product> findByAdminId(Long adminId, Pageable pageable);

    Page<Product> findByCategoryId(Long categoryId, Pageable pageable);



}
