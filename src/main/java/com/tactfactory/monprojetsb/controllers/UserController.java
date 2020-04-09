package com.tactfactory.monprojetsb.controllers;

import com.tactfactory.monprojetsb.dao.UserRepository;
import com.tactfactory.monprojetsb.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(value = "user")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @RequestMapping(value = {"/index", "/"}, method = RequestMethod.GET)
    public String index(Model model) {
        model.addAttribute("page", "User index");
        model.addAttribute("items", userRepository.findAll());
        return "user/index";
    }

    @GetMapping(value = {"/create"})
    public String createGet(Model model) {
        model.addAttribute("page", "Create user");
        return "user/create";
    }

    @PostMapping(value = {"/create"})
    public String createPost(@ModelAttribute User user) {
        if (user != null) {
            userRepository.save(user);
        }
        return "redirect:index";
    }

    @PostMapping(value = {"/delete"})
    public String delete(@ModelAttribute User user) {
        if (user != null) {
            userRepository.delete(user);
        }
        return "redirect:index";
    }

    @GetMapping(value = {"/show/{id}"})
    public String details(Model model, @PathVariable long id) {
        model.addAttribute("page", "User Details");
        model.addAttribute("item", userRepository.findById(id).isPresent() ? userRepository.findById(id).get() : null);
        return "user/show";
    }
}
