package com.e_commerce.productservice;

import com.e_commerce.productservice.models.Product;
import com.e_commerce.productservice.repositories.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ProductserviceApplication implements CommandLineRunner {

	public ProductRepository productRepository;

	public ProductserviceApplication(ProductRepository productRepository) {
		this.productRepository = productRepository;
	}

	@Override
	public void run(String... args) throws Exception {

		Product product1 = new Product();
		product1.setName("Iphone");
		product1.setPrice(80000);
		product1.setCategory("Mobiles");
		product1.setDescription("good mobiles");
		productRepository.save(product1);

	}

	public static void main(String[] args) {
		SpringApplication.run(ProductserviceApplication.class, args);
	}

}
