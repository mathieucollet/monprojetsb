package com.tactfactory.monprojetsb.services;

import com.tactfactory.monprojetsb.dao.ProductRepository;
import com.tactfactory.monprojetsb.dao.UserRepository;
import com.tactfactory.monprojetsb.entities.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@EntityScan(basePackages = "com.tactfactory.monprojetsb")
@ComponentScan(basePackages = "com.tactfactory.monprojetsb")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
public class ProductServiceTest {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    public void cleanProductTable() {
        userRepository.deleteAll();
        productRepository.deleteAll();
    }

    @Test
    public void productTestInsertionAddARecord() {
        long beforeInsert = productRepository.count();
        productService.save(new Product());
        long afterInsert = productRepository.count();
        assertEquals(beforeInsert + 1, afterInsert);
    }

    @Test
    public void productTestInsertionDoNotAlterData() {
        Product product = new Product("Nom", 10.0F);
        productService.save(product);
        Product createdProduct = productRepository.findById(product.getId()).get();
        assertTrue(isValid(createdProduct, product));
    }

    @Test
    public void productTestUpdateDoNoAlterData() {
        Product product = new Product("Nom", 10.0F);
        productService.save(product);
        Product createdProduct = productRepository.findById(product.getId()).get();
        createdProduct.setName("NouveauNom");
        productService.save(createdProduct);
        Product updatedProduct = productRepository.findById(product.getId()).get();
        assertTrue(isValid(updatedProduct, product));
    }

    @Test
    public void productTestFindDataAreTheGoodOnes() {
        Product product = new Product("Nom", 10.0F);
        productRepository.save(product);
        Product createdProduct = productService.findById(product.getId());
        assertTrue(isValid(createdProduct, product));
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
        Product product = new Product();
        productRepository.save(product);
        long beforeInsert = productRepository.count();
        productService.delete(product);
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
