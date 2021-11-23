package com.alkemy.blogAPI.service;

import com.alkemy.blogAPI.entity.Category;
import com.alkemy.blogAPI.entity.Post;
import com.alkemy.blogAPI.repository.CategoryRepository;
import java.util.*;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {
    
    @Autowired
    private CategoryRepository categoryRepository;
    
    @Transactional
    public void saveCategory(Category category){
        category.setEnabled(true);
        categoryRepository.save(category);
    }
    
    @Transactional
    public Iterable<Object[]> getCategories(){
        return categoryRepository.getCategories();
    }
    
    @Transactional
    public void deleteCategory(Integer id){
        try{
            categoryRepository.deleteById(id);
        }catch(Exception e){
            e.printStackTrace();
            e.getMessage();
        }
    }
    
    @Transactional
    public Optional<Category> findById(Integer categoryId){
        return categoryRepository.findById(categoryId);
    }
    
    @Transactional
    public List<Post> getPostsForCategory(Integer categoryId){
        Category category = categoryRepository.getById(categoryId);
        if(category != null){
            return category.getPosts();
        }else{
            return null;
        }
    }
}
