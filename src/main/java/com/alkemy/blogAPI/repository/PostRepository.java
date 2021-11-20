package com.alkemy.blogAPI.repository;

import com.alkemy.blogAPI.entity.Post;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post,Integer>{
    public Iterable<Object[]> findByTitle(String title);
    
    public Optional<Post> findById(Integer id);
    
    public Post getById(Integer peliculaId);
    
    @Query("SELECT p.postId, p.title, p.image, p.category.categoryId,p.user.userId FROM Post p")
    public Iterable<Object[]> getPosts();
    
    @Query("SELECT p.postId, p.title, p.image, p.category.categoryId,p.user.userId FROM Post p ORDER BY p.dateCreation DESC")
    public Iterable <Object[]> getPostsOrderDESC();
    
}
