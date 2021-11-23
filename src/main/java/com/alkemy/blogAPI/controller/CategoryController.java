package com.alkemy.blogAPI.controller;

import com.alkemy.blogAPI.entity.Category;
import com.alkemy.blogAPI.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @DeleteMapping(path = "delete/:{id}")
    public String delete(@PathVariable("id") Integer id){
        try {
            categoryService.deleteCategory(id);
            return "Categoría "+categoryService.findById(1).get().getName()+" eliminada";
        } catch (Exception e) {
            return "Categoría "+categoryService.findById(1).get().getName()+" no eliminada";
        }
    }
}
