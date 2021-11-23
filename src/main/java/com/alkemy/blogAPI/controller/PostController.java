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
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/posts")
public class PostController {

    PostService postService;
    UserService userService;
    CategoryService categoryService;

    public PostController(PostService postService, UserService userService, CategoryService categoryService) {
        this.postService = postService;
        this.userService = userService;
        this.categoryService = categoryService;
    }

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
    public String save(@ModelAttribute Post post, HttpServletRequest request, HttpSession session)throws IOException{
        boolean res=util.regexImage(post.getImage());
        
        try{
            if(!res){
                throw new Exception("Url no corresponde a una imagen");
            }
            post.setImage(post.getImage());
            String email=(String) session.getAttribute("email");
            post.setUser(userService.findByEmail(email));
            postService.savePost(post);
            
            return "Post número "+post.getPostId()+" guardado";
        }catch(Exception e){
            return e.getMessage();

        }
    }
    
    
    @DeleteMapping(path = "delete/:{id}")
    public String delete(@PathVariable("id") Integer id){
        try {
            postService.delete(id);
            return "Post número "+id+" eliminado";
        } catch (Exception e) {
            return "Post número "+id+" no pudo ser eliminado";
        }
    }
    
    @PutMapping(path = "/:{id}")
    public String patch(@ModelAttribute Post postDetail,HttpServletRequest request, @PathVariable("id") Integer id){
        Optional<Post> post = postService.findById(id);
        try {
            if(!post.isPresent()){
                return ("Post número "+id+" no encontrado");
            }
            
            post.get().setTitle(postDetail.getTitle());
            post.get().setContent(postDetail.getContent());
            post.get().setImage(postDetail.getImage());
            post.get().setCategory(postDetail.getCategory());
            
            postService.savePost(post.get());
            return "Post número "+id+" modificado";
        } catch (Exception e) {
            return e.getMessage();
        }
    }
    
}
