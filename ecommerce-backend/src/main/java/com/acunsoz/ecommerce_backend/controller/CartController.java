package com.acunsoz.ecommerce_backend.controller;

import com.acunsoz.ecommerce_backend.model.dto.AddToCartRequest;
import com.acunsoz.ecommerce_backend.model.dto.CartDto;
import com.acunsoz.ecommerce_backend.model.entity.CartItem;
import com.acunsoz.ecommerce_backend.model.entity.User;
import com.acunsoz.ecommerce_backend.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @PostMapping
    public ResponseEntity<CartDto> addToCart(
     @AuthenticationPrincipal User user,
     @RequestBody AddToCartRequest request
     )
    {
        CartDto updatedCart = cartService.addToCart(user, request);
        return ResponseEntity.ok(updatedCart);

    }

    @GetMapping
    public ResponseEntity<CartDto> getCart(@AuthenticationPrincipal User user)
    {
        return ResponseEntity.ok(cartService.getCart(user));
    }

    @DeleteMapping("/items/{productId}")
    public ResponseEntity<CartDto> removeFromCart(
            @AuthenticationPrincipal User user,
            @PathVariable Long productId
    )
    {
        CartDto updatedCart = cartService.removeFromCart(user,productId);
        return ResponseEntity.ok(updatedCart);
    }

    @PutMapping("/items/{productId}")
    public ResponseEntity<CartDto> updateItemQuantity(
            @AuthenticationPrincipal User user,
            @PathVariable Long productId,
            @RequestParam Integer quantity
    )
    {
        CartDto updateCart = cartService.updateItemQuantity(user,productId,quantity);
        return ResponseEntity.ok(updateCart);
    }

}
