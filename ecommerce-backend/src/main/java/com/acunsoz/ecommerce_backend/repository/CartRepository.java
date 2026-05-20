package com.acunsoz.ecommerce_backend.repository;

import com.acunsoz.ecommerce_backend.model.entity.Cart;
import com.acunsoz.ecommerce_backend.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart , Long>
{
    Optional<Cart> findByUser(User user);

}
