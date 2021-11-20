package com.alkemy.blogAPI.repository;

import com.alkemy.blogAPI.entity.Category;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer>{
    public Optional<Category> findById(Integer id);
    public Category getById(Integer categoryID);
}
