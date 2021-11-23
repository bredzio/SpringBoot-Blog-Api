package com.alkemy.blogAPI.service;

import com.alkemy.blogAPI.entity.Post;
import com.alkemy.blogAPI.repository.PostRepository;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PostService {
    @Autowired
    private PostRepository postRepository;
    
    @Transactional
    public Iterable<Post> findAll(){
        return postRepository.findAll();
    }
    
    @Transactional
    public Iterable<Object[]> getPosts(){
        return postRepository.getPosts();
    }
    
    @Transactional
    public Optional<Post> findById(Integer id){
        return postRepository.findById(id);
    }
    
    @Transactional
    public Post savePost(Post post){
        post.setEnabled(true);
        return postRepository.save(post);
    }
    
    @Transactional
    public Optional<Post> findByTitle(String title){
        return postRepository.findByTitle(title);
    }
    
    @Transactional
    public void delete(Integer id){
        try{
            postRepository.deleteById(id);
        }catch(Exception e){
            e.printStackTrace();
            e.getMessage();
        }    
    }
    
    @Transactional
    public List<Post> getPostOrderDESC(){
        return postRepository.getPostsOrderDESC();
        
    }
    
}
