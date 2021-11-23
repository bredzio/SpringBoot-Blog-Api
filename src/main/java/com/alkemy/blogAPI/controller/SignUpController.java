package com.alkemy.blogAPI.controller;

import com.alkemy.blogAPI.entity.SignUp;
import com.alkemy.blogAPI.service.SignUpService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "auth/sign_up")
@AllArgsConstructor
public class SignUpController {
    private final SignUpService signUpService;
    
    @PostMapping
    public String register(@RequestBody SignUp signUp){
        return signUpService.signUp(signUp);
    }
    
    @GetMapping(path="confirm")
    public String confirm(@RequestParam("token") String token){
        return signUpService.confirmToken(token);
    }
}
