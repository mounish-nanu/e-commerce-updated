package com.e_commerce.productservice.services;

import com.e_commerce.productservice.client.UserClient;
import com.e_commerce.productservice.dtos.UserDto;
import org.springframework.stereotype.Service;

@Service
public class ProductFacade {
    private final UserClient userClient;

    public ProductFacade(UserClient userClient) { this.userClient = userClient; }

    public UserDto fetchUser(Long userId, String authHeader) {
        return userClient.getUserById(userId, authHeader); // pass it to WebClient call
    }
}
