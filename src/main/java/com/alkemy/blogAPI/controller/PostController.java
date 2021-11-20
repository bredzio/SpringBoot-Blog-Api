package com.alkemy.blogAPI.controller;

import com.alkemy.blogAPI.entity.Post;
import com.alkemy.blogAPI.service.CategoryService;
import com.alkemy.blogAPI.service.PostService;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/posts")
public class PostController {
    
    @Autowired
    private PostService postService;
    
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
    
    @GetMapping(value = "", params="categoryId")
    public List<Post> getBygenero(@RequestParam("categoryId") Integer categoryId){
        return categoryService.getPostsForCategory(categoryId);
    }
    
    @PostMapping("save")
    public Post save(@RequestParam("file") MultipartFile image, @ModelAttribute Post post){
        if(!image.isEmpty()){
            Path imagesPath = Paths.get("src//main//resources//static//images");
            String absolutPath = imagesPath.toFile().getAbsolutePath();
            try {
                byte[] bytes = image.getBytes();
                Path route = Paths.get(absolutPath + image.getOriginalFilename());
                Files.write(route, bytes);
                post.setImage(image.getOriginalFilename());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return postService.savePost(post);
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
    
}
