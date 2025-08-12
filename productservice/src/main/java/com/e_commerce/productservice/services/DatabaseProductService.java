package com.e_commerce.productservice.services;

import com.e_commerce.productservice.models.Product;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface DatabaseProductService {
    public Product getProduct(int id);
    public List<Product> getProducts();
    public Product updateProduct(Product product, int id);
    public String deleteProduct(int id);
    public String createProduct(Product product);

}
