package com.acunsoz.ecommerce_backend.repository;

import com.acunsoz.ecommerce_backend.model.entity.Order;
import com.acunsoz.ecommerce_backend.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order,Long> {

    List<Order> findByUserOrderByOrderDateDesc(User user);

}
