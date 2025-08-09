package com.e_commerce.userservice.controllers;

import com.e_commerce.userservice.Exceptions.UserAlreadyExistsException;
import com.e_commerce.userservice.Exceptions.WrongPasswordException;
import com.e_commerce.userservice.dtos.LoginResponseDto;
import com.e_commerce.userservice.dtos.RequestStatus;
import com.e_commerce.userservice.models.User;
import com.e_commerce.userservice.services.UserService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public String addUser(@RequestBody User user) throws UserAlreadyExistsException {

        try{
            return userService.createUser(user);
        }catch(UserAlreadyExistsException e){
            return e.getMessage();
        }

    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody User user) {
        try {
            String token = userService.loginUser(user);
            LoginResponseDto loginResponseDto = new LoginResponseDto();
            loginResponseDto.setRequestStatus(RequestStatus.SUCCESS);
            MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
            map.add(HttpHeaders.AUTHORIZATION, "Bearer " + token);
            ResponseEntity<LoginResponseDto> responseEntity = new ResponseEntity<>(loginResponseDto, map, HttpStatus.OK);
            return responseEntity;
        }catch(Exception e){
            LoginResponseDto loginResponseDto = new LoginResponseDto();
            loginResponseDto.setRequestStatus(RequestStatus.FAILURE);
            ResponseEntity<LoginResponseDto> responseEntity = new ResponseEntity<>(loginResponseDto, null , HttpStatus.BAD_REQUEST);
            return responseEntity;
        }
    }

    @PostMapping("/logout/{token}")
    public String logout(@PathVariable String token) {
        return userService.deleteSession(token);
    }
}
