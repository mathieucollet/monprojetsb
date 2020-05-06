package com.tactfactory.monprojetsb.services;

import com.tactfactory.monprojetsb.ApplicationTests;
import com.tactfactory.monprojetsb.dao.ProductRepository;
import com.tactfactory.monprojetsb.entities.Product;
import com.tactfactory.monprojetsb.mocks.repositories.MockitoProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ActiveProfiles("test")
@TestPropertySource(locations = {"classpath:application-test.properties"})
@SpringBootTest(classes = ApplicationTests.class)
public class ProductServiceTest {

    @Autowired
    private ProductService productService;

    @MockBean
    private ProductRepository productRepository;

    private Product productEntity;

    @BeforeEach
    public void setUp() throws Exception {
        final MockitoProductRepository productMock = new MockitoProductRepository(this.productRepository);
        productMock.initialize();
        productEntity = productMock.entity;
    }

    @Test
    public void productTestInsertionAddARecord() {
        long beforeInsert = productRepository.count();
        productService.save(productEntity);
        long afterInsert = productRepository.count();
        assertEquals(beforeInsert + 1, afterInsert);
    }

    @Test
    public void productTestInsertionDoNotAlterData() {
        productService.save(productEntity);
        Product createdProduct = productRepository.findById(productEntity.getId()).get();
        assertTrue(isValid(createdProduct, productEntity));
    }

    @Test
    public void productTestUpdateDoNoAlterData() {
        productService.save(productEntity);
        Product createdProduct = productRepository.findById(productEntity.getId()).get();
        createdProduct.setName("NouveauNom");
        productService.save(createdProduct);
        Product updatedProduct = productRepository.findById(productEntity.getId()).get();
        assertTrue(isValid(updatedProduct, createdProduct));
    }

    @Test
    public void productTestFindDataAreTheGoodOnes() {
        productRepository.save(productEntity);
        Product createdProduct = productService.findById(productEntity.getId());
        assertTrue(isValid(createdProduct, productEntity));
    }

    @Test
    public void productTestFindAllDataAreTheGoodOnes() {
        List<Product> products = new ArrayList();
        products.add(new Product("A", 10.0F));
        products.add(new Product("B", 20.0F));
        products.add(new Product("C", 30.0F));
        productRepository.saveAll(products);

        List<Product> createdProducts = productService.findAll();
        boolean isValid = listIsValid(products, createdProducts);
        assertTrue(isValid);
    }

    @Test
    public void productTestDeleteRemoveARecord() {
        productRepository.save(productEntity);
        long beforeInsert = productRepository.count();
        productService.delete(productEntity);
        long afterInsert = productRepository.count();
        assertEquals(beforeInsert, afterInsert + 1);
    }

    @Test
    public void productTestDeleteRemoveTheCorrectRecord() {
        List<Product> products = new ArrayList();
        products.add(new Product("A", 10.0F));
        products.add(new Product("B", 20.0F));
        products.add(new Product("C", 30.0F));
        productRepository.saveAll(products);
        productService.delete(products.get(1));
        products.remove(1);
        List<Product> createdProducts = productRepository.findAll();
        boolean isValid = listIsValid(products, createdProducts);
        assertTrue(isValid);
    }

    private boolean isValid(Product createdProduct, Product product) {
        boolean isValid = false;
        if (createdProduct.getName().equals(product.getName()) && createdProduct.getPrice().equals(product.getPrice())) {
            isValid = true;
        }
        return isValid;
    }

    private boolean listIsValid(List<Product> products, List<Product> createdProducts) {
        boolean isValid = false;
        for (int i = 0; i < products.size(); i++) {
            isValid = isValid(createdProducts.get(i), products.get(i));
            if (!isValid) {
                break;
            }
        }
        return isValid;
    }

}
