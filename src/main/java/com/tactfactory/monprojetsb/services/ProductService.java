package com.tactfactory.monprojetsb.services;

import com.tactfactory.monprojetsb.dao.ProductRepository;
import com.tactfactory.monprojetsb.entities.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService implements BaseService<Product, Long> {

    @Autowired
    private ProductRepository productRepository;

    @Override
    public Product save(Product item) {
        return this.productRepository.save(item);
    }

    @Override
    public void delete(Product item) {
        this.productRepository.delete(item);
    }

    @Override
    public Product findById(Long id) {
        return productRepository.findById(id).isPresent() ? productRepository.findById(id).get() : null;
    }

    @Override
    public List<Product> findAll() {
        return productRepository.findAll();
    }
}
