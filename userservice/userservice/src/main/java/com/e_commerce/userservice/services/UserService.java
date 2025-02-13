package com.e_commerce.userservice.services;

import com.e_commerce.userservice.Exceptions.UserAlreadyExistsException;
import com.e_commerce.userservice.models.User;

public interface UserService {
    public String createUser(User user) throws UserAlreadyExistsException;
    public String loginUser(User user);
    public String deleteSession(String sessionId);
}
