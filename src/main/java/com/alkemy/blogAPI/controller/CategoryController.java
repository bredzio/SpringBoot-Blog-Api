package com.alkemy.blogAPI.controller;

import com.alkemy.blogAPI.entity.Category;
import com.alkemy.blogAPI.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<?> save(@ModelAttribute Category category){
        try {
            categoryService.saveCategory(category);
            return new ResponseEntity<>("Categoría creada con exito", HttpStatus.ACCEPTED);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping(path = "delete/:{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Integer id){
        try {
            categoryService.deleteCategory(id);
            return new ResponseEntity<>("Categoría "+categoryService.findById(id).get().getName()+" eliminada",HttpStatus.ACCEPTED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }
}
