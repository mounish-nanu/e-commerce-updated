package com.e_commerce.productservice.exceptions;

import com.e_commerce.productservice.dtos.Exceptiondto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

@org.springframework.web.bind.annotation.ControllerAdvice
public class ControllerAdvice {

        @ExceptionHandler(NotFoundException.class)
        private ResponseEntity<Exceptiondto> handleNotFoundException(
                NotFoundException notFoundExceptions
        ){
            return new ResponseEntity<>(
                    new Exceptiondto(HttpStatus.NOT_FOUND, notFoundExceptions.getMessage()),
                    HttpStatus.NOT_FOUND
            );
        }
    }

