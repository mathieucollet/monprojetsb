package com.tactfactory.monprojetsb.dao;

import com.tactfactory.monprojetsb.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
