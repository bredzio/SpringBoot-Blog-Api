package com.alkemy.blogAPI.repository;

import com.alkemy.blogAPI.entity.Post;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post,Integer>{
    public Iterable<Object[]> findByTitle(String title);
    
    @Query("SELECT p FROM Post p WHERE p.enabled=true and p.postId = ?1")
    public Optional<Post> findById(Integer id);
    
    public Post getById(Integer postId);
    
    @Query("SELECT p.postId, p.title, p.image, p.category.categoryId,p.user.userId FROM Post p")
    public Iterable<Object[]> getPosts();
    
    @Query("SELECT p.postId, p.title, p.image, p.category.categoryId,p.dateCreation FROM Post p WHERE p.enabled=true ORDER BY p.dateCreation DESC")
    public Iterable <Object[]> getPostsOrderDESC();
    
}
