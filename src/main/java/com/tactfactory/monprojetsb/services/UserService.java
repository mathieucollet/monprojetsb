package com.tactfactory.monprojetsb.services;

import com.tactfactory.monprojetsb.dao.UserRepository;
import com.tactfactory.monprojetsb.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;

@Service
public class UserService implements BaseService<User, Long> {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User save(@ModelAttribute User item) {
        return this.userRepository.save(item);
    }

    @Override
    public void delete(User item) {
        this.userRepository.delete(item);
    }

    @Override
    public User findById(Long id) {
        return userRepository.findById(id).isPresent() ? userRepository.findById(id).get() : null;
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }
}
