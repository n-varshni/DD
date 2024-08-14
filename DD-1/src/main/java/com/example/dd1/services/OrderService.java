package com.example.dd1.services;

import com.example.dd1.entity.AddToCart;
import com.example.dd1.entity.Order;
import com.example.dd1.repository.AddToCartRepo;
import com.example.dd1.repository.OrderRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import java.util.Base64;
@Service
public class OrderService {

    @Autowired
    private OrderRepo orderRepository;

    @Autowired
    private AddToCartRepo addToCartRepository;

    public Order createOrderFromCart(Long customerId) {
        List<AddToCart> cartItems = addToCartRepository.findByCustomerId(customerId);

        // Create a new order
        Order order = new Order();
        order.setCustomer(cartItems.get(0).getCustomer());
        order.setProductIds(cartItems.stream().map(item -> item.getProduct().getId()).collect(Collectors.toList()));
        order.setProductNames(cartItems.stream().map(item -> item.getProduct().getName()).collect(Collectors.toList()));
        order.setFilters(cartItems.stream().map(AddToCart::getFilter).collect(Collectors.toList()));
        
        // Set images as byte[]
        List<byte[]> images = cartItems.stream()
            .map(AddToCart::getImage)
            .collect(Collectors.toList());
        order.setImages(images);

        // Set quantities
        List<Integer> quantities = cartItems.stream()
            .map(AddToCart::getQuantity)
            .collect(Collectors.toList());
        order.setQuantities(quantities);

        order.setOrderDate(LocalDate.now());
        order.setDeliveryDate(LocalDate.now().plusDays(5));
        order.setTotalCost(cartItems.stream().mapToDouble(item -> item.getQuantity() * item.getProduct().getPrice()).sum());

        // Save the order
        Order createdOrder = orderRepository.save(order);

        // Clear the cart after placing the order
        addToCartRepository.deleteAll(cartItems);

        return createdOrder;
    }

    public List<Order> getOrdersByCustomerId(Long customerId) {
        List<Order> orders = orderRepository.findByCustomerId(customerId);
        // Convert byte[] images to Base64 strings for frontend display
        orders.forEach(order -> {
            List<String> encodedImages = order.getImages().stream()
                .map(image -> Base64.getEncoder().encodeToString(image))
                .collect(Collectors.toList());
            order.setImages(encodedImages.stream().map(Base64.getDecoder()::decode).collect(Collectors.toList()));
        });
        return orders;
    }

    public void deleteOrder(Long orderId) {
        orderRepository.deleteById(orderId);
    }
    
    public long getOrderCount() {
        return orderRepository.count();
    }

}
