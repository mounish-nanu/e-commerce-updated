package com.e_commerce.userservice.dtos;

import com.e_commerce.userservice.models.User;

public record UserDto(Long id, String name) {

    public static UserDto from(User u) {
        return new UserDto(u.getId(), u.getEmail());
    }
}
