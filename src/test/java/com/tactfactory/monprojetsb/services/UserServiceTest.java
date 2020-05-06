package com.tactfactory.monprojetsb.services;

import com.tactfactory.monprojetsb.ApplicationTests;
import com.tactfactory.monprojetsb.dao.UserRepository;
import com.tactfactory.monprojetsb.entities.User;
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
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @MockBean
    private UserRepository userRepository;

    private User userEntity;

    @BeforeEach
    public void setUp() throws Exception {
        final MockitoUserRepository mock = new MockitoUserRepository(this.userRepository);
        mock.initialize();
        userEntity = mock.entity;
    }

    @Test
    public void userTestInsertionAddARecord() {
        long beforeInsert = userRepository.count();
        userService.save(userEntity);
        long afterInsert = userRepository.count();
        assertEquals(beforeInsert + 1, afterInsert);
    }

    @Test
    public void userTestInsertionDoNotAlterData() {
        userService.save(userEntity);
        User createdUser = userRepository.findById(userEntity.getId()).get();
        assertTrue(isValid(createdUser, userEntity));
    }

    @Test
    public void userTestUpdateDoNoAlterData() {
        userService.save(userEntity);
        User createdUser = userRepository.findById(userEntity.getId()).get();
        createdUser.setFirstname("NouveauPrénom");
        userService.save(createdUser);
        User updatedUser = userRepository.findById(userEntity.getId()).get();
        assertTrue(isValid(updatedUser, createdUser));
    }

    @Test
    public void userTestFindDataAreTheGoodOnes() {
        userRepository.save(userEntity);
        User createdUser = userService.findById(userEntity.getId());
        assertTrue(isValid(createdUser, userEntity));
    }

    @Test
    public void userTestFindAllDataAreTheGoodOnes() {
        List<User> users = new ArrayList();
        users.add(new User("A", "AA"));
        users.add(new User("B", "BB"));
        users.add(new User("C", "CC"));
        userRepository.saveAll(users);

        List<User> createdUsers = userService.findAll();
        boolean isValid = listIsValid(users, createdUsers);
        assertTrue(isValid);
    }

    @Test
    public void userTestDeleteRemoveARecord() {
        userRepository.save(userEntity);
        long beforeInsert = userRepository.count();
        userService.delete(userEntity);
        long afterInsert = userRepository.count();
        assertEquals(beforeInsert, afterInsert + 1);
    }

    @Test
    public void userTestDeleteRemoveTheCorrectRecord() {
        List<User> users = new ArrayList();
        users.add(new User("A", "AA"));
        users.add(new User("B", "BB"));
        users.add(new User("C", "CC"));
        userRepository.saveAll(users);
        userService.delete(users.get(1));
        users.remove(1);
        List<User> createdUsers = userRepository.findAll();
        boolean isValid = listIsValid(users, createdUsers);
        assertTrue(isValid);
    }

    private boolean isValid(User createdUser, User user) {
        boolean isValid = false;
        if (createdUser.getFirstname().equals(user.getFirstname()) && createdUser.getLastname().equals(user.getLastname())) {
            isValid = true;
        }
        return isValid;
    }

    private boolean listIsValid(List<User> users, List<User> createdUsers) {
        boolean isValid = false;
        for (int i = 0; i < users.size(); i++) {
            isValid = isValid(createdUsers.get(i), users.get(i));
            if (!isValid) {
                break;
            }
        }
        return isValid;
    }
}
