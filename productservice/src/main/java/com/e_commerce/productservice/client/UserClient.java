package com.e_commerce.productservice.client;

import com.e_commerce.productservice.dtos.UserDto;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;



@Component
public class UserClient {

    private final WebClient userServiceWebClient;

    public UserClient(WebClient userServiceWebClient) {
        this.userServiceWebClient = userServiceWebClient;
    }

    public UserDto getUserById(Long id, String authHeader) {
        return userServiceWebClient.get()
                .uri("/users/{id}", id)
                .headers(h -> {
                    if (authHeader != null && !authHeader.isBlank()) {
                        h.set("Authorization", authHeader);
                    }
                })
                .retrieve()
                .bodyToMono(UserDto.class)
                .block(); // fine in MVC
    }
}