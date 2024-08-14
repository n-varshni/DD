package com.example.dd1.controller;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.example.dd1.entity.customer;
import com.example.dd1.services.customerservices;

@RestController
@RequestMapping("/api/customers")
@CrossOrigin(origins = "http://localhost:3000")
public class customercontroller {

    @Autowired
    private customerservices cs;

    @PostMapping("/signup")
    public ResponseEntity<customer> signUp(@RequestBody customer customer) {
        customer newCustomer = cs.signUp(customer);
        return ResponseEntity.ok(newCustomer);
    }

    @PostMapping("/login")
    public ResponseEntity<customer> login(@RequestBody customer loginRequest) {
        String email = loginRequest.getEmail();
        String password = loginRequest.getPassword();
        Optional<customer> customer = cs.login(email, password);
        return customer.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(401).build());
    }

    @GetMapping("/current")
    public ResponseEntity<customer> getCurrentCustomer(@RequestParam String email) {
        Optional<customer> customer = cs.getCustomerByEmail(email);
        return customer.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(404).build());
    }

    @PostMapping("/uploadProfileImage/{customerId}")
    public ResponseEntity<customer> uploadProfileImage(@PathVariable Long customerId, @RequestParam("image") MultipartFile image) throws IOException {
        Optional<customer> customerOptional = cs.findById(customerId);
        if (customerOptional.isPresent()) {
            customer customer = customerOptional.get();
            customer.setProfileImage(image.getBytes());
            cs.saveCustomer(customer);
            return ResponseEntity.ok(customer);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/update/{customerId}")
    public ResponseEntity<customer> updateCustomer(@PathVariable Long customerId, 
                                                   @RequestParam("name") String name, 
                                                   @RequestParam("email") String email, 
                                                   @RequestParam("address") String address, 
                                                   @RequestParam("phoneNumber") String phoneNumber, 
                                                   @RequestParam(value = "image", required = false) MultipartFile image) throws IOException {
        Optional<customer> customerOptional = cs.findById(customerId);
        if (customerOptional.isPresent()) {
            customer customer = customerOptional.get();
            customer.setName(name);
            customer.setEmail(email);
            customer.setAddress(address);
            customer.setPhoneNumber(phoneNumber);
            if (image != null && !image.isEmpty()) {
                customer.setProfileImage(image.getBytes());
            }
            cs.saveCustomer(customer);
            return ResponseEntity.ok(customer);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/image/{customerId}")
    public ResponseEntity<byte[]> getProfileImage(@PathVariable Long customerId) {
        Optional<customer> customerOptional = cs.findById(customerId);
        if (customerOptional.isPresent()) {
            customer customer = customerOptional.get();
            byte[] image = customer.getProfileImage();
            if (image != null) {
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.IMAGE_JPEG); // Adjust if PNG
                headers.setContentLength(image.length);
                return new ResponseEntity<>(image, headers, HttpStatus.OK);
            } else {
                return ResponseEntity.noContent().build();
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<customer>> getAllCustomers() {
        List<customer> customers = cs.findAll();
        return ResponseEntity.ok(customers);
    }
    
    @GetMapping("/count")
    public ResponseEntity<Long> getCustomerCount() {
        long count = cs.countCustomers();
        return ResponseEntity.ok(count);
    }

}
