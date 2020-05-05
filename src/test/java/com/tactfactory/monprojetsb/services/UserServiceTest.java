package com.tactfactory.monprojetsb.services;

import com.tactfactory.monprojetsb.dao.UserRepository;
import com.tactfactory.monprojetsb.entities.User;
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
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    public void cleanUserTable() {
        userRepository.deleteAll();
    }

    @Test
    public void userTestInsertionAddARecord() {
        long beforeInsert = userRepository.count();
        userService.save(new User());
        long afterInsert = userRepository.count();
        assertEquals(beforeInsert + 1, afterInsert);
    }

    @Test
    public void userTestInsertionDoNotAlterData() {
        User user = new User("Prénom", "Nom");
        userService.save(user);
        User createdUser = userRepository.findById(user.getId()).get();
        assertTrue(isValid(createdUser, user));
    }

    @Test
    public void userTestUpdateDoNoAlterData() {
        User user = new User("Prénom", "Nom");
        userService.save(user);
        User createdUser = userRepository.findById(user.getId()).get();
        createdUser.setFirstname("NouveauPrénom");
        userService.save(createdUser);
        User updatedUser = userRepository.findById(user.getId()).get();
        assertTrue(isValid(updatedUser, user));
    }

    @Test
    public void userTestFindDataAreTheGoodOnes() {
        User user = new User("Prénom", "Nom");
        userRepository.save(user);
        User createdUser = userService.findById(user.getId());
        assertTrue(isValid(createdUser, user));
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
        User user = new User();
        userRepository.save(user);
        long beforeInsert = userRepository.count();
        userService.delete(user);
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
