package com.alkemy.blogAPI.service;

import com.alkemy.blogAPI.entity.User;
import com.alkemy.blogAPI.repository.UserRepository;
import com.alkemy.blogAPI.security.token.TokenConfirmation;
import com.alkemy.blogAPI.security.token.TokenConfirmationService;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService implements UserDetailsService{
    private final static String userNotFound = "No se encontró usuario con ese email %s";

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final TokenConfirmationService tokenConfirmationService;

    
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException(String.format(userNotFound, email)));    
    }
    
    public String enableUser(User user) {
        boolean existUser = userRepository.findByEmail(user.getEmail()).isPresent();

        if (existUser) {
            throw new IllegalStateException("Email ok");
        }

        String encodedPassword = bCryptPasswordEncoder.encode(user.getPassword());

        user.setPassword(encodedPassword);

        userRepository.save(user);

        String token = UUID.randomUUID().toString();

        TokenConfirmation tokenConfirmation = new TokenConfirmation(token,LocalDateTime.now(),LocalDateTime.now().plusMinutes(15),user);

        tokenConfirmationService.saveConfirmationToken(tokenConfirmation);

        return token;
    }
    
    public int enableAppUser(String email) {
        return userRepository.enableAppUser(email);
    }
}
