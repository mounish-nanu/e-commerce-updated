package com.e_commerce.productservice.controllers;

import com.e_commerce.productservice.models.Product;
import com.e_commerce.productservice.services.DatabaseProductService;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {
    private final DatabaseProductService databaseProductService;

    public ProductController(@Qualifier("DatabaseProductServiceImpl") DatabaseProductService databaseProductService) {
        this.databaseProductService = databaseProductService;
    }
    @GetMapping("/{id}")
    public Product getProductById(@PathVariable int id) {
        return databaseProductService.getProduct(id);
    }
//@GetMapping("/{id}")
//public ResponseEntity<Product> getProductById(
//        @PathVariable int id,
//        @RequestHeader(value = "Authorization", required = false) String authHeader) {
//
//    if (authHeader == null || !authHeader.startsWith("Bearer ")) {
//        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Missing or invalid Authorization header");
//    }
//
//    String token = authHeader.substring(7); // drop "Bearer "
//    try {
//        Claims claims = JwtUtil.validateTokenAndGetClaims(token);
//        // Optional: use claims (e.g., tenant, roles, subject)
//        // String user = claims.getSubject();
//        Product product = databaseProductService.getProduct(id);
//        return ResponseEntity.ok(product);
//    } catch (JwtException ex) { // covers expired/invalid signature/etc.
//        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid or expired token");
//    }
//}
    @GetMapping
    public List<Product> getAllProducts(){
        return databaseProductService.getProducts();
    }
    @DeleteMapping("/{id}")
    public String deleteProductById(@PathVariable int id){
        return databaseProductService.deleteProduct(id);
    }
    @PutMapping("/{id}")
    public Product updateProductById(
            @RequestBody Product product ,
            @PathVariable int id){
        return databaseProductService.updateProduct(product,id);
    }
    @PostMapping
    public String createProduct(@RequestBody Product product){
        return databaseProductService.createProduct(product);
    }
}
