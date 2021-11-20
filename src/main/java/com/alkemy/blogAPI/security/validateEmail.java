package com.alkemy.blogAPI.security;

import java.util.function.Predicate;
import org.springframework.stereotype.Service;

@Service
public class validateEmail implements Predicate<String>{
   @Override
    public boolean test(String s) {
        return true;
    } 
}
