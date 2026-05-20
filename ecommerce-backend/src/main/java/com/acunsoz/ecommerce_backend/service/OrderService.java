package com.acunsoz.ecommerce_backend.service;

import com.acunsoz.ecommerce_backend.model.dto.OrderDto;
import com.acunsoz.ecommerce_backend.model.entity.*;
import com.acunsoz.ecommerce_backend.model.enums.OrderStatus;
import com.acunsoz.ecommerce_backend.model.mapper.IOrderMapper;
import com.acunsoz.ecommerce_backend.repository.CartRepository;
import com.acunsoz.ecommerce_backend.repository.OrderRepository;
import com.acunsoz.ecommerce_backend.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final CartService cartService;
    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final IOrderMapper orderMapper;

    @Transactional
    public OrderDto placeOrder(User user)
    {

        Cart cart = cartService.getOrCreateCart(user);

        if (cart.getItems().isEmpty())
        {
            throw new RuntimeException("Sepetiniz boş sipariş verilemez!");
        }

        Order order = new Order();
        order.setUser(user);
        order.setTotalAmount(cart.getTotalPrice());

        List<OrderItem> orderItems = cart.getItems().stream().map(cartItem -> {
            Product product = cartItem.getProduct();

            if (product.getStock()<cartItem.getQuantity())
            {
                throw new RuntimeException("Yetersiz stok! Ürün: "+ product.getName()+ " (Kalan Stok: "+ product.getStock()+")");
            }

            product.setStock(product.getStock()-cartItem.getQuantity());
            productRepository.save(product);

            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setProduct(product);
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setPrice(cartItem.getPrice());

            return orderItem;
        }).collect(Collectors.toList());

        order.setItems(orderItems);

        Order savedOrder = orderRepository.save(order);

        cart.getItems().clear();
        cart.setTotalPrice(BigDecimal.ZERO);
        cartRepository.save(cart);

        return orderMapper.toDto(savedOrder);

    }

    public List<OrderDto> getUserOrders(User user)
    {
        List<Order> orders = orderRepository.findByUserOrderByOrderDateDesc(user);
        return orders.stream()
                .map(orderMapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public OrderDto updateOrderStatus(Long orderId, OrderStatus newStatus)
    {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(()-> new RuntimeException("Sipariş bulunamadı!"));

        order.setStatus(newStatus);

        Order savedOrder = orderRepository.save(order);

        return orderMapper.toDto(savedOrder);

    }


}
