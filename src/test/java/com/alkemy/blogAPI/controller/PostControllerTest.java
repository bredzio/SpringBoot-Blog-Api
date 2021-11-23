package com.alkemy.blogAPI.controller;

import com.alkemy.blogAPI.entity.Category;
import com.alkemy.blogAPI.entity.Post;
import com.alkemy.blogAPI.entity.User;
import com.alkemy.blogAPI.service.CategoryService;
import com.alkemy.blogAPI.service.PostService;
import com.alkemy.blogAPI.service.UserService;
import com.alkemy.blogAPI.util.Util;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

class PostControllerTest {

    PostService postServiceMock = Mockito.mock(PostService.class);
    CategoryService categoryServiceMock=Mockito.mock(CategoryService.class);
    UserService userServiceMock=Mockito.mock(UserService.class);


    @Autowired
    PostController postController=new PostController(postServiceMock,userServiceMock,categoryServiceMock);

    @Autowired
    private Util util;

    private List<Post> lista;

    List<Post> postDeports = new ArrayList<>();

    @BeforeEach
    void setUp() {
        Category deporte = new Category();
        deporte.setCategoryId(1);
        deporte.setName("Deporte");

        User user = new User();
        user.setUserId(1);
        user.setEmail("brunoredzio@hotmail.com");

        Post mockPost = new Post();
        mockPost.setContent("Desarollando mi api");
        mockPost.setImage("http://www.avajava.com/images/avajavalogo.jpg");
        mockPost.setTitle("Mi api");
        mockPost.setPostId(1);
        mockPost.setCategory(deporte);

        Post mockPost2 = new Post();
        mockPost2.setContent("Desarollando mi api 2");
        mockPost2.setImage("http://www.avajava.com/images/avajavalogo.jpg");
        mockPost2.setTitle("Mi api 2");
        mockPost2.setPostId(2);


        postDeports.add(mockPost);

        Mockito.when(categoryServiceMock.getPostsForCategory(1)).thenReturn(postDeports);
        Mockito.when(postServiceMock.findById(1)).thenReturn(Optional.of(mockPost));
        Mockito.when(postServiceMock.findById(2)).thenReturn(Optional.of(mockPost2));
        Mockito.when(postServiceMock.findByTitle("Mi api")).thenReturn(Optional.of(mockPost));
        Mockito.when(userServiceMock.findByEmail("brunoredzio@hotmail.com")).thenReturn(user);

    }

    @Test
    void getListPosts() {
        postController.getListPosts();
    }

    @Test
    void findById() {
        Optional<Post> post=postController.findById(1);
        Assertions.assertEquals("Mi api",post.get().getTitle());
    }

    @Test
    void findByTitle() {
        Optional<Post> post=postController.findByTitle("Mi api");
        Assertions.assertEquals("Mi api",post.get().getTitle());
    }

    @Test
    void getBycategory() {
        List<Post> posts=postController.getBycategory(1);
        Assertions.assertEquals("Mi api",posts.get(0).getTitle());
    }

    @Test
    void save() throws IOException {

    }

    @Test
    void delete() {
    }

    @Test
    void patch() {
    }


}