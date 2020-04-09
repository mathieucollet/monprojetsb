package com.tactfactory.monprojetsb.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
public class User {
    @Id
    @GeneratedValue
    private long id;
    private String firstname;
    private String lastname;
    @OneToMany
    private List<Product> products = new ArrayList<>();
}
