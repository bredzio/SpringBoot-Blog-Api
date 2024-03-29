package com.alkemy.blogAPI.repository;

import com.alkemy.blogAPI.entity.Category;
import java.util.Optional;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer>{
    public Optional<Category> findById(Integer id);
    public Category getById(Integer categoryID);
    
    @Query("SELECT c.categoryId, c.name FROM Category c")
    public Iterable<Object[]> getCategories();
}
