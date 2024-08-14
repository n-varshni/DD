package com.example.dd1.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.dd1.entity.AddToCart;
import com.example.dd1.services.AddToCartService;

@RestController
@RequestMapping("/api/cart")
@CrossOrigin(origins = "http://localhost:3000")
public class AddToCartController {

    @Autowired
    private AddToCartService cartService;

    @PostMapping
    public ResponseEntity<AddToCart> addToCart(@RequestBody CartRequest cartRequest) {
        AddToCart cart = cartService.addToCart(
                cartRequest.getCustomerId(),
                cartRequest.getProductId(),
                cartRequest.getQuantity(),
                cartRequest.getFilter(),
                cartRequest.getImage(),
                cartRequest.getTotal()
        );

        if (cart != null) {
            return ResponseEntity.ok(cart);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping("/items/{customerId}")
    public ResponseEntity<List<AddToCart>> getCartItems(@PathVariable Long customerId) {
        List<AddToCart> items = cartService.getCartItems(customerId);
        return ResponseEntity.ok(items);
    }

    @PutMapping("/{cartItemId}")
    public ResponseEntity<AddToCart> updateCartItem(
            @PathVariable Long cartItemId,
            @RequestBody CartRequest cartRequest) {
        AddToCart updatedCart = cartService.updateCartItem(
                cartItemId,
                cartRequest.getQuantity(),
                cartRequest.getFilter(),
                cartRequest.getImage()
        );

        if (updatedCart != null) {
            return ResponseEntity.ok(updatedCart);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping("/{cartItemId}")
    public ResponseEntity<Void> deleteCartItem(@PathVariable Long cartItemId) {
        cartService.deleteCartItem(cartItemId);
        return ResponseEntity.noContent().build();
    }
}

// CartRequest DTO
class CartRequest {
    private Long customerId;
    private Long productId;
    private int quantity;
    private String filter;
    private byte[] image;
    private double total;

    // Getters and Setters
    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getFilter() {
        return filter;
    }

    public void setFilter(String filter) {
        this.filter = filter;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }
}