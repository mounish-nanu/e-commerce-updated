package com.e_commerce.productservice.controllers;

import com.e_commerce.productservice.models.Product;
import com.e_commerce.productservice.services.DatabaseProductService;
import com.e_commerce.productservice.services.DatabaseProductServiceImpl;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {
    private final DatabaseProductService databaseProductService;

    public ProductController(@Qualifier("DatabaseProductServiceImpl") DatabaseProductServiceImpl databaseProductServiceImpl
    , DatabaseProductService databaseProductService) {
        this.databaseProductService = databaseProductService;
    }
    @GetMapping("/{id}")
    public Product getProductById(@PathVariable int id) {
        return databaseProductService.getProduct(id);
    }
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
