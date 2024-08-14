package com.example.dd1.services;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.dd1.entity.AddToCart;
import com.example.dd1.entity.Product;
import com.example.dd1.entity.customer;
import com.example.dd1.repository.AddToCartRepo;
import com.example.dd1.repository.ProductRepo;
import com.example.dd1.repository.customerrepo;

@Service
public class AddToCartService {

    @Autowired
    private AddToCartRepo cartRepository;

    @Autowired
    private customerrepo customerRepository;

    @Autowired
    private ProductRepo productRepository;

    public AddToCart addToCart(Long customerId, Long productId, int quantity, String filter, byte[] image, double total) {
        Optional<customer> customerOptional = customerRepository.findById(customerId);
        Optional<Product> productOptional = productRepository.findById(productId);

        if (customerOptional.isPresent() && productOptional.isPresent()) {
            AddToCart cart = new AddToCart();
            cart.setCustomer(customerOptional.get());
            cart.setProduct(productOptional.get());
            cart.setQuantity(quantity);
            cart.setFilter(filter);
            cart.setImage(image);
            cart.setTotal(total);
            return cartRepository.save(cart);
        }
        return null;
    }

    public List<AddToCart> getCartItems(Long customerId) {
        return cartRepository.findByCustomerId(customerId);
    }

    public AddToCart updateCartItem(Long cartItemId, int quantity, String filter, byte[] image) {
        Optional<AddToCart> cartOptional = cartRepository.findById(cartItemId);
        if (cartOptional.isPresent()) {
            AddToCart cart = cartOptional.get();
            cart.setQuantity(quantity);
            cart.setFilter(filter);
            cart.setImage(image);
            return cartRepository.save(cart);
        }
        return null;
    }

    public void deleteCartItem(Long cartItemId) {
        cartRepository.deleteById(cartItemId);
    }
}
