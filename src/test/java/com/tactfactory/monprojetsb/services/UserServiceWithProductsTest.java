package com.tactfactory.monprojetsb.services;

import com.tactfactory.monprojetsb.dao.ProductRepository;
import com.tactfactory.monprojetsb.dao.UserRepository;
import com.tactfactory.monprojetsb.entities.Product;
import com.tactfactory.monprojetsb.entities.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.dao.InvalidDataAccessApiUsageException;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@EntityScan(basePackages = "com.tactfactory.monprojetsb")
@ComponentScan(basePackages = "com.tactfactory.monprojetsb")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
public class UserServiceWithProductsTest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    @BeforeEach
    public void cleanUserTable() {
        userRepository.deleteAll();
        productRepository.deleteAll();
    }

    @Test
    public void userTestInsertionWithProductDoNotAddARecord() {
        long beforeInsert = productRepository.count();
        List<Product> products = new ArrayList();
        products.add(new Product());
        userService.save(new User("A", "AA", products));
        long afterInsert = productRepository.count();
        assertEquals(beforeInsert, afterInsert);
    }

    @Test
    public void userTestInsertionWithProductAlterData() {
        List<Product> products = new ArrayList();
        products.add(new Product());
        User user = new User("Prénom", "Nom", products);
        userService.save(user);
        Assertions.assertThrows(InvalidDataAccessApiUsageException.class, () -> userRepository.flush());
    }

    @Test
    public void userTestInsertionWithExistingProduct() {
        long beforeInsert = userRepository.count();
        Product product = productRepository.save(new Product());
        List<Product> products = new ArrayList();
        products.add(product);
        userService.save(new User("Prénom", "Nom", products));
        long afterInsert = userRepository.count();
        assertEquals(beforeInsert + 1, afterInsert);
    }

    @Test
    public void userTestInsertionWithExistingProductDoNotAlterData() {
        Product product = productRepository.save(new Product());
        List<Product> products = new ArrayList();
        products.add(product);
        User user = new User("Prénom", "Nom", products);
        userService.save(user);
        User createdUser = userRepository.findById(user.getId()).get();
        boolean isValid = isValid(createdUser, user);
        assertTrue(isValid);
    }

    @Test
    public void userTestInsertionWithProductUpdate() {
        Product product = productRepository.save(new Product());
        long beforeInsert = productRepository.count();
        product.setName("NouveauNom");
        List<Product> products = new ArrayList();
        products.add(product);
        User user = new User("Prénom", "Nom", products);
        userService.save(user);
        long afterInsert = productRepository.count();
        assertEquals(beforeInsert, afterInsert);
    }

    @Test
    public void userTestInsertionWithProductUpdateDoNotAlterData() {
        Product product = new Product("Nom", 10.0F);
        productRepository.save(product);
        product.setName("NouveauNom");
        List<Product> products = new ArrayList();
        products.add(product);
        User user = new User("Prénom", "Nom", products);
        userService.save(user);
        Product updatedProduct = productRepository.findById(product.getId()).get();
        boolean isValid = productIsValid(updatedProduct, product);
        assertTrue(isValid);
    }

    @Test
    public void userTestInsertionWithProductDelete() {
        Product product1 = new Product("A", 10.0F);
        Product product2 = new Product("B", 20.0F);
        productRepository.save(product1);
        productRepository.save(product2);
        long beforeInsert = productRepository.count();
        List<Product> products = new ArrayList();
        products.add(product1);
        products.add(product2);
        User user = new User("C", "CC", products);
        userRepository.save(user);
        User userCreated = userRepository.findById(user.getId()).get();
        userCreated.getProducts().remove(0);
        long afterInsert = productRepository.count();
        assertEquals(beforeInsert, afterInsert);
    }

    @Test
    public void userTestInsertionWithProductDeleteDoNotAlterData() {
        Product product1 = new Product("A", 10.0F);
        Product product2 = new Product("B", 20.0F);
        productRepository.save(product1);
        productRepository.save(product2);
        List<Product> products = new ArrayList();
        products.add(product1);
        products.add(product2);
        User user = new User("C", "CC", products);
        userRepository.save(user);
        User userCreated = userRepository.findById(user.getId()).get();
        long deletedProductId = userCreated.getProducts().get(0).getId();
        userCreated.getProducts().remove(0);
        Product productDeleted = productRepository.findById(deletedProductId).get();
        boolean isValid = productIsValid(productDeleted, product1);
        assertTrue(isValid);
    }

    private boolean isValid(User createdUser, User user) {
        boolean isValid = false;
        if (createdUser.getFirstname().equals(user.getFirstname()) && createdUser.getLastname().equals(user.getLastname())) {
            isValid = true;
        }
        return isValid;
    }

    private boolean productIsValid(Product createdProduct, Product product) {
        boolean isValid = false;
        if (createdProduct.getName().equals(product.getName()) && createdProduct.getPrice().equals(product.getPrice())) {
            isValid = true;
        }
        return isValid;
    }

}
