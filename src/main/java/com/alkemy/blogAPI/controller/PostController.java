package com.alkemy.blogAPI.controller;

import com.alkemy.blogAPI.entity.Post;
import com.alkemy.blogAPI.service.CategoryService;
import com.alkemy.blogAPI.service.PostService;
import com.alkemy.blogAPI.service.UserService;
import com.alkemy.blogAPI.util.Util;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import javax.servlet.http.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/posts")
public class PostController {

    @Autowired
    PostService postService;

    @Autowired
    UserService userService;

    @Autowired
    CategoryService categoryService;

    @Autowired
    private Util util;
    
   
    @GetMapping()
    public List<Post> getListPosts(){
        return postService.getPostOrderDESC();
    }
    
    @GetMapping("/:{id}")
    public Optional<Post> findById(@PathVariable("id") Integer id){       
        return postService.findById(id);
    }
    
    @GetMapping(params="title")
    public Optional<Post> findByTitle(@RequestParam("title") String title){
        return postService.findByTitle(title);
    }
    
    @GetMapping(value = "", params="category")
    public List<Post> getBycategory(@RequestParam("category") Integer categoryId){
        return categoryService.getPostsForCategory(categoryId);
    }
    
    @PostMapping()
    public ResponseEntity<?> save(@ModelAttribute Post post, HttpServletRequest request, HttpSession session)throws IOException{
        boolean res=util.regexImage(post.getImage());
        
        try{
            if(!res){
                return new ResponseEntity<>("Url no corresponde a una imagen", HttpStatus.BAD_REQUEST);
            }
            post.setImage(post.getImage());
            String email=(String) session.getAttribute("email");
            post.setUser(userService.findByEmail(email));
            postService.savePost(post);
            return new ResponseEntity<>("Post creado con exito", HttpStatus.CREATED);
        }catch(Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }
    
    
    @DeleteMapping(path = "delete/:{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Integer id){
        try {
            postService.delete(id);
            return new ResponseEntity<>("Post número "+id+" eliminado",HttpStatus.ACCEPTED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }
    
    @PutMapping(path = "/:{id}")
    public  ResponseEntity<?> patch(@ModelAttribute Post postDetail,HttpServletRequest request, @PathVariable("id") Integer id){
        Optional<Post> post = postService.findById(id);
        try {
            if(!post.isPresent()){
                return new ResponseEntity<>("Post número "+id+" no encontrado",HttpStatus.NOT_FOUND);
            }
            
            post.get().setTitle(postDetail.getTitle());
            post.get().setContent(postDetail.getContent());
            post.get().setImage(postDetail.getImage());
            post.get().setCategory(postDetail.getCategory());
            
            postService.savePost(post.get());
            return new ResponseEntity<>("Post número "+id+" modificado",HttpStatus.ACCEPTED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }
    
}
