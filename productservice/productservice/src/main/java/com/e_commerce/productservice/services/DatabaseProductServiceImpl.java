package com.e_commerce.productservice.services;

import com.e_commerce.productservice.exceptions.NotFoundException;
import com.e_commerce.productservice.models.Product;
import com.e_commerce.productservice.repositories.ProductRepository;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service("DatabaseProductServiceImpl")
public class DatabaseProductServiceImpl implements DatabaseProductService {

    private final ProductRepository productRepository;
    private final RedisTemplate<String, Object> redisTemplate;

    public DatabaseProductServiceImpl(ProductRepository productRepository,
                                      RedisTemplate<String, Object> redisTemplate) {
        this.productRepository = productRepository;
        this.redisTemplate = redisTemplate;
    }

    @Override
    public Product getProduct(int id) {
        Optional<Product> product1 = (Optional<Product>)redisTemplate.opsForHash().get("PRODUCTS",id);
        if (product1.isPresent()) {
            return product1.orElse(null);
        }
        else {
            Optional<Product> product = productRepository.findById(id);
            redisTemplate.opsForHash().put("PRODUCTS",id,product);
            return product.orElse(null);
        }
    }
    public List<Product> getProducts() {
        return productRepository.findAll();
    }
    public String deleteProduct(int id) {
        productRepository.deleteById(id);
        return "Product deleted with id: " + id;
    }

    @Override


    public Product updateProduct(Product product, int id) {
        Optional<Product> optionalProduct = productRepository.findById(id);

        if (optionalProduct.isPresent()) {
            Product existingProduct = optionalProduct.get();

            existingProduct.setPrice(product.getPrice());
            existingProduct.setDescription(product.getDescription());
            existingProduct.setCategory(product.getCategory());
            existingProduct.setName(product.getName());

            return productRepository.save(existingProduct);
        } else {
//            new NotFoundException("Product with id " + id + " not found")
             return null;
        }
    }

    public String createProduct(Product product) {
        Product product1 = new Product();
        product1.setName(product.getName());
        product1.setDescription(product.getDescription());
        product1.setPrice(product.getPrice());
        product1.setCategory(product.getCategory());

        productRepository.save(product1);

        return "Product created";
    }
}
