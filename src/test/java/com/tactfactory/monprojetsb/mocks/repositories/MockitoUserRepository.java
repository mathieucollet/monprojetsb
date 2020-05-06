package com.tactfactory.monprojetsb.mocks.repositories;

import com.tactfactory.monprojetsb.dao.UserRepository;
import com.tactfactory.monprojetsb.entities.User;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.mockito.stubbing.Answer;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;

public class MockitoUserRepository {
    protected final UserRepository repository;

    public User entity;

    public List<User> users = new ArrayList();

    public MockitoUserRepository(UserRepository repository) {
        this.repository = repository;

        this.entity = new User();
        this.entity.setFirstname("f1");
        this.entity.setLastname("l1");
    }

    public void initialize() {
        Mockito.when(repository.findById(any())).thenAnswer((Answer<Optional<User>>) invocation -> users.stream().filter(user -> user.getId() == (long) invocation.getArgument(0)).findFirst());

        Mockito.when(repository.count()).thenAnswer((Answer<Long>) invocation -> MockitoUserRepository.this.count());

        Mockito.when(repository.findAll()).thenReturn(MockitoUserRepository.this.users);

        Mockito.doAnswer((i) -> {
            if (i.getMethod().getName().equals("delete")) {
                MockitoUserRepository.this.users.remove(users.stream().filter(user -> user.getId() == i.<User>getArgument(0).getId()).findFirst().orElse(null));
            }
            return null;
        }).when(this.repository).delete(ArgumentMatchers.any());

        Mockito.when(repository.save(any())).thenAnswer((Answer<User>) invocation -> {
            User user = invocation.getArgument(0);
            long currentId = MockitoUserRepository.this.users.size() + 1;
            user.setId(currentId);
            MockitoUserRepository.this.users.add(user);
            return user;
        });

        Mockito.when(repository.saveAll(any())).thenAnswer((Answer<List<User>>) invocation -> {
            List<User> argUsers = invocation.getArgument(0);
            for (User user : argUsers) {
                user.setId(MockitoUserRepository.this.count() + 1);
                MockitoUserRepository.this.users.add(user);
            }
            return MockitoUserRepository.this.users;
        });
    }

    private Long count() {
        return (long) users.size();
    }
}
