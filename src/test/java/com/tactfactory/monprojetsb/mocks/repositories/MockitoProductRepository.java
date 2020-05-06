package com.tactfactory.monprojetsb.mocks.repositories;

import com.tactfactory.monprojetsb.dao.ProductRepository;
import com.tactfactory.monprojetsb.entities.Product;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.mockito.stubbing.Answer;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;

public class MockitoProductRepository {
    protected final ProductRepository repository;

    public Product entity;

    public List<Product> products = new ArrayList();

    public MockitoProductRepository(ProductRepository repository) {
        this.repository = repository;

        this.entity = new Product();
        this.entity.setName("n1");
        this.entity.setPrice(10.0F);
    }

    public void initialize() {
        Mockito.when(repository.findById(any())).thenAnswer((Answer<Optional<Product>>) invocation -> products.stream().filter(product -> product.getId() == (long) invocation.getArgument(0)).findFirst());

        Mockito.when(repository.count()).thenAnswer((Answer<Long>) invocation -> MockitoProductRepository.this.count());

        Mockito.when(repository.findAll()).thenReturn(MockitoProductRepository.this.products);

        Mockito.doAnswer((i) -> {
            if (i.getMethod().getName().equals("delete")) {
                MockitoProductRepository.this.products.remove(products.stream().filter(product -> product.getId() == i.<Product>getArgument(0).getId()).findFirst().orElse(null));
            }
            return null;
        }).when(this.repository).delete(ArgumentMatchers.any());

        Mockito.when(repository.save(any())).thenAnswer((Answer<Product>) invocation -> {
            Product product = invocation.getArgument(0);
            long currentId = MockitoProductRepository.this.products.size() + 1;
            product.setId(currentId);
            MockitoProductRepository.this.products.add(product);
            return product;
        });

        Mockito.when(repository.saveAll(any())).thenAnswer((Answer<List<Product>>) invocation -> {
            List<Product> argProducts = invocation.getArgument(0);
            for (Product product : argProducts) {
                product.setId(MockitoProductRepository.this.count() + 1);
                MockitoProductRepository.this.products.add(product);
            }
            return MockitoProductRepository.this.products;
        });
    }

    private Long count() {
        return (long) products.size();
    }
}
