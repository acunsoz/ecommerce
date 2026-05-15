package com.acunsoz.ecommerce_backend.repository;

import com.acunsoz.ecommerce_backend.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {

    Optional<User> findByUsername (String username);
}
