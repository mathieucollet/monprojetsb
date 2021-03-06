package com.tactfactory.monprojetsb.dao;

import com.tactfactory.monprojetsb.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
}
