package com.example.dd1.services;

import com.example.dd1.entity.Product;
import com.example.dd1.repository.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepo productRepository;

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product getProductById(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    public byte[] getProductImage(Long id) {
        Product product = getProductById(id);
        return product != null ? product.getImage() : null;
    }

    public Product addProduct(String name, String description, double price, MultipartFile imageFile) throws IOException {
        Product product = new Product();
        product.setName(name);
        product.setDescription(description);
        product.setPrice(price);
        product.setImage(imageFile.getBytes());
        return productRepository.save(product);
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }
    
    public long countProducts() {
        return productRepository.count();
    }

}
