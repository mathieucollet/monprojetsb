package com.tactfactory.monprojetsb.services;

import com.tactfactory.monprojetsb.ApplicationTests;
import com.tactfactory.monprojetsb.dao.ProductRepository;
import com.tactfactory.monprojetsb.dao.UserRepository;
import com.tactfactory.monprojetsb.entities.Product;
import com.tactfactory.monprojetsb.entities.User;
import com.tactfactory.monprojetsb.mocks.repositories.MockitoProductRepository;
import com.tactfactory.monprojetsb.mocks.repositories.MockitoUserRepository;
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
public class UserServiceWithProductsTest {

    @Autowired
    private UserService userService;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private ProductRepository productRepository;

    private Product productEntity;
    private User userEntity;

    @BeforeEach
    public void setUp() throws Exception {
        final MockitoUserRepository userMock = new MockitoUserRepository(this.userRepository);
        final MockitoProductRepository productMock = new MockitoProductRepository(this.productRepository);
        userMock.initialize();
        productMock.initialize();
        userEntity = userMock.entity;
        productEntity = productMock.entity;
    }

    @Test
    public void userTestInsertionWithProductDoNotAddARecord() {
        long beforeInsert = productRepository.count();
        List<Product> products = new ArrayList();
        products.add(productEntity);
        userEntity.setProducts(products);
        userService.save(userEntity);
        long afterInsert = productRepository.count();
        assertEquals(beforeInsert, afterInsert);
    }

    @Test
    public void userTestInsertionWithProductDoNotAlterData() {
        List<Product> products = new ArrayList();
        products.add(productEntity);
        userEntity.setProducts(products);
        userService.save(userEntity);
        User createdUser = userRepository.findById(userEntity.getId()).get();
        assertTrue(isValid(createdUser, userEntity));
    }

    @Test
    public void userTestInsertionWithExistingProduct() {
        long beforeInsert = userRepository.count();
        Product product = productRepository.save(productEntity);
        List<Product> products = new ArrayList();
        products.add(product);
        userEntity.setProducts(products);
        userService.save(userEntity);
        long afterInsert = userRepository.count();
        assertEquals(beforeInsert + 1, afterInsert);
    }

    @Test
    public void userTestInsertionWithExistingProductDoNotAlterData() {
        Product product = productRepository.save(productEntity);
        List<Product> products = new ArrayList();
        products.add(product);
        userEntity.setProducts(products);
        userService.save(userEntity);
        User createdUser = userRepository.findById(userEntity.getId()).get();
        boolean isValid = isValid(createdUser, userEntity);
        assertTrue(isValid);
    }

    @Test
    public void userTestInsertionWithProductUpdate() {
        Product product = productRepository.save(productEntity);
        long beforeInsert = productRepository.count();
        productEntity.setName("NouveauNom");
        List<Product> products = new ArrayList();
        products.add(productEntity);
        userEntity.setProducts(products);
        userService.save(userEntity);
        long afterInsert = productRepository.count();
        assertEquals(beforeInsert, afterInsert);
    }

    @Test
    public void userTestInsertionWithProductUpdateDoNotAlterData() {
        productRepository.save(productEntity);
        productEntity.setName("NouveauNom");
        List<Product> products = new ArrayList();
        products.add(productEntity);
        userEntity.setProducts(products);
        userService.save(userEntity);
        Product updatedProduct = productRepository.findById(productEntity.getId()).get();
        boolean isValid = productIsValid(updatedProduct, productEntity);
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
        userEntity.setProducts(products);
        userRepository.save(userEntity);
        User userCreated = userRepository.findById(userEntity.getId()).get();
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
        userEntity.setProducts(products);
        userRepository.save(userEntity);
        User userCreated = userRepository.findById(userEntity.getId()).get();
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
