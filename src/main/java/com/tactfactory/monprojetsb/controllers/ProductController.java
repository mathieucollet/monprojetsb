package com.tactfactory.monprojetsb.controllers;

import com.tactfactory.monprojetsb.dao.ProductRepository;
import com.tactfactory.monprojetsb.entities.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(value = "product")
public class ProductController {

    @Autowired
    private ProductRepository productRepository;

    @RequestMapping(value = {"/index", "/"}, method = RequestMethod.GET)
    public String index(Model model) {
        model.addAttribute("page", "Product index");
        model.addAttribute("items", productRepository.findAll());
        return "product/index";
    }

    @GetMapping(value = {"/create"})
    public String createGet(Model model) {
        model.addAttribute("page", "Create product");
        return "product/create";
    }

    @PostMapping(value = {"/create"})
    public String createPost(@ModelAttribute Product product) {
        if (product != null) {
            productRepository.save(product);
        }
        return "redirect:index";
    }

    @PostMapping(value = {"/delete"})
    public String delete(@ModelAttribute Product product) {
        if (product != null) {
            productRepository.delete(product);
        }
        return "redirect:index";
    }

    @GetMapping(value = {"/show/{id}"})
    public String details(Model model, @PathVariable long id) {
        model.addAttribute("page", "Product Details");
        model.addAttribute("item", productRepository.findById(id).isPresent() ? productRepository.findById(id).get() : null);
        return "product/show";
    }
}
