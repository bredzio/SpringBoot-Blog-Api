package com.alkemy.blogAPI.controller;

import com.alkemy.blogAPI.entity.Post;
import com.alkemy.blogAPI.service.CategoryService;
import com.alkemy.blogAPI.service.PostService;
import com.alkemy.blogAPI.service.UserService;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/posts")
public class PostController {
    
    @Autowired
    private PostService postService;
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private CategoryService categoryService;
    
   
    @GetMapping()
    public Iterable<Object[]> getListPosts(){
        return postService.getPostOrderDESC();
    }
    
    @GetMapping("/:{id}")
    public Optional<Post> findById(@PathVariable("id") Integer id){       
        return postService.findById(id);
    }
    
    @GetMapping(params="title")
    public Iterable<Object[]> findByTitle(@RequestParam("title") String title){
        return postService.findByTitle(title);
    }
    
    @GetMapping(value = "", params="category")
    public List<Post> getBygenero(@RequestParam("category") Integer categoryId){
        return categoryService.getPostsForCategory(categoryId);
    }
    
    @PostMapping()
    public String save(@ModelAttribute Post post, HttpServletRequest request, HttpSession session)throws IOException{
        String imageUrl = post.getImage();
        String extension=imageUrl.substring(imageUrl.lastIndexOf("."));
        
        Pattern p = Pattern.compile("(.*/)*.+\\.(png|jpg|gif|bmp|jpeg|PNG|JPG|GIF|BMP)$");
        Matcher page = p.matcher(imageUrl);
        boolean res=page.matches();
        
        try{
            if(!res){
                throw new Exception("Url no corresponde a una imagen");
            }
            post.setImage(imageUrl);
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
