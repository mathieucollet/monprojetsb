package com.tactfactory.monprojetsb.controllers;

import com.tactfactory.monprojetsb.dao.UserDao;
import com.tactfactory.monprojetsb.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
@RequestMapping(value="user")
public class UserController {

    @Autowired
    private UserDao userDao;

    @RequestMapping(value={"/index", "/"}, method = RequestMethod.GET)
    public String index(Model model) {
        model.addAttribute("page", "User index");
        model.addAttribute("items", userDao.findAll());
        return "user/index";
    }
}