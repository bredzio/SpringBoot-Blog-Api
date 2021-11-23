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
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

class PostControllerTest {

    @Mock
    private PostService PostServiceMock;

    @InjectMocks
    PostController postController;

    @Autowired
    private Util util;

    private Post mockPost;
    private Category deporte;
    private User user;

    private List<Post> lista;

    List<Post> postDeports = new ArrayList<>();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);

        deporte = new Category();
        deporte.setCategoryId(1);
        deporte.setName("Deporte");

        user = new User();
        user.setUserId(1);
        user.setEmail("brunoredzio@hotmail.com");

        mockPost = new Post();
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

    }

    @Test
    void getListPosts() {
        when(PostServiceMock.getPostOrderDESC()).thenReturn(Arrays.asList(mockPost));
        assertNotNull(postController.getListPosts());
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