package com.alkemy.blogAPI.entity;

import lombok.*;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class SignUp {
    private final String email;
    private final String password;
}
