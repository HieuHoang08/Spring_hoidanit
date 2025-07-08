package com.hh.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.hh.domain.Product;
import com.hh.domain.User;
import com.hh.repository.ProductRepository;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final UploadService uploadService;

    public ProductService(ProductRepository productRepository, UploadService uploadService) {
        this.productRepository = productRepository;
        this.uploadService = uploadService;
    }

    public Product handleSaveProduct(Product product) {
        return productRepository.save(product);
    }

    public Product createProduct(Product product) {
        return this.productRepository.save(product);
    }
    public List<Product> getAllProducts() {
        return this.productRepository.findAll();
    }
    public Product getProductById(Long id) {
        return this.productRepository.findById(id).orElse(null);
    }

    public Product updateProduct(Product product) {
        return this.productRepository.save(product);
    }
}
