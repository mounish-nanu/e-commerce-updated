package com.e_commerce.productservice.dtos;

import org.springframework.http.HttpStatus;

public class Exceptiondto {
    private HttpStatus errorCode;
    private String message;

    public Exceptiondto(HttpStatus errorCode, String message) {
        this.errorCode = errorCode;
        this.message = message;
    }

    public void setErrorCode(HttpStatus errorCode) {
        this.errorCode = errorCode;
    }
    public HttpStatus getErrorCode() {
        return errorCode;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public String getMessage() {
        return message;
    }
}
