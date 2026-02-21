package com.acunsoz.ecommerce_backend.repository;

import com.acunsoz.ecommerce_backend.model.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long>
{
    List<Product> findByNameStartingWithIgnoreCase(String prefix);


}
