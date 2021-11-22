package com.alkemy.blogAPI.controller;

import com.alkemy.blogAPI.entity.Category;
import com.alkemy.blogAPI.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/categories")
public class CategoryController {
    
    @Autowired
    private CategoryService categoryService;
    
    @GetMapping()
    public Iterable<Object[]> getCategories(){
        return categoryService.getCategories();
    }
    
    @PostMapping()
    public void save(@ModelAttribute Category category){ 
        categoryService.saveCategory(category);
    }
}
