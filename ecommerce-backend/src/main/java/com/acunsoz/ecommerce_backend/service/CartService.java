package com.acunsoz.ecommerce_backend.service;

import com.acunsoz.ecommerce_backend.model.dto.AddToCartRequest;
import com.acunsoz.ecommerce_backend.model.dto.CartDto;
import com.acunsoz.ecommerce_backend.model.dto.CartItemDto;
import com.acunsoz.ecommerce_backend.model.entity.Cart;
import com.acunsoz.ecommerce_backend.model.entity.CartItem;
import com.acunsoz.ecommerce_backend.model.entity.Product;
import com.acunsoz.ecommerce_backend.model.entity.User;
import com.acunsoz.ecommerce_backend.model.mapper.ICartMapper;
import com.acunsoz.ecommerce_backend.repository.CartRepository;
import com.acunsoz.ecommerce_backend.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final ICartMapper cartMapper;

    public Cart getOrCreateCart(User user)
    {
        return cartRepository.findByUser(user).orElseGet(() ->
        {
           Cart newCart = new Cart();
           newCart.setUser(user);
           newCart.setTotalPrice(BigDecimal.ZERO);
           return cartRepository.save(newCart);
        });
    }

    @Transactional
    public CartDto addToCart(User user, AddToCartRequest request)
    {

        Cart cart = getOrCreateCart(user);
        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new RuntimeException("Ürün bulunamadı"));

        Optional<CartItem> existingItem = cart.getItems().stream()
                .filter(item -> item.getProduct().getId().equals(product.getId()))
                .findFirst();

        if (existingItem.isPresent())
        {
            CartItem item = existingItem.get();
            item.setQuantity(item.getQuantity() + request.getQuantity());
        }
        else {

            CartItem newItem = new CartItem();
            newItem.setCart(cart);
            newItem.setProduct(product);
            newItem.setQuantity(request.getQuantity());
            newItem.setPrice(product.getPrice());

            cart.getItems().add(newItem);

        }

        calculateTotalPrice(cart);

        Cart savedCart = cartRepository.save(cart);

        return cartMapper.toDto(savedCart);

    }

    private void calculateTotalPrice(Cart cart)
    {
        BigDecimal total = BigDecimal.ZERO;

        for (CartItem item : cart.getItems())
        {
            BigDecimal itemTotal = item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity()));
            total = total.add(itemTotal);
        }
        cart.setTotalPrice(total);
    }

    public CartDto getCart(User user)
    {
        Cart cart = getOrCreateCart(user);
        return cartMapper.toDto(cart);
    }

    @Transactional
    public CartDto removeFromCart(User user, Long productId)
    {
        Cart cart = getOrCreateCart(user);

        boolean removed = cart.getItems().removeIf(item -> item.getProduct().getId().equals(productId));

        if (removed)
        {
            calculateTotalPrice(cart);
            cart = cartRepository.save(cart);
        }

        return cartMapper.toDto(cart);

    }

    @Transactional
    public CartDto updateItemQuantity(User user , Long productId,Integer newQuantity)
    {

        Cart cart = getOrCreateCart(user);

        if (newQuantity<1)
        {
            throw  new IllegalArgumentException("Urun miktarı en az 1 olmali");
        }

        Optional<CartItem> existingItem = cart.getItems().stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst();

        if (existingItem.isPresent())
        {
            CartItem item = existingItem.get();
            item.setQuantity(newQuantity);

            calculateTotalPrice(cart);
            cart = cartRepository.save(cart);

        }
        else {

            throw new RuntimeException("Bu ürün sepetinizde bulunmuyor");

        }

        return cartMapper.toDto(cart);

    }


}
