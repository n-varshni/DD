package com.example.dd1.services;

import org.springframework.stereotype.Service;

@Service
public class AdminloginService {
    private final String ADMIN_EMAIL = "varshni@gmail.com";
    private final String ADMIN_PASSWORD = "1907";

    public boolean validateAdmin(String email, String password) {
        return ADMIN_EMAIL.equals(email) && ADMIN_PASSWORD.equals(password);
    }
}

