package com.alkemy.blogAPI.service;

import com.alkemy.blogAPI.entity.RoleUser;
import com.alkemy.blogAPI.entity.SignUp;
import com.alkemy.blogAPI.entity.User;
import com.alkemy.blogAPI.security.token.TokenConfirmation;
import com.alkemy.blogAPI.security.token.TokenConfirmationService;
import com.alkemy.blogAPI.security.validateEmail;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class SignUpService {
    private final UserService userService;
    private final TokenConfirmationService tokenConfirmationService;
    private final validateEmail validateEmail;
    
    public String signUp(SignUp signUp) {
        boolean emailOk = validateEmail.test(signUp.getEmail());

        if (!emailOk) {
            throw new IllegalStateException("Email no válido");
        }
        
        String token = userService.enableUser(new User(signUp.getEmail(),signUp.getPassword(),RoleUser.USER));
       
        return token;
    }
    
    @Transactional
    public String confirmToken(String token) {
        TokenConfirmation tokenConfirmation = tokenConfirmationService.getToken(token).orElseThrow(() -> new IllegalStateException("Token no encontrado"));

        if (tokenConfirmation.getConfirmedAt() != null) {
            throw new IllegalStateException("Email confirmado");
        }

        LocalDateTime expiredAt = tokenConfirmation.getExpiresAt();

        if (expiredAt.isBefore(LocalDateTime.now())) {
            throw new IllegalStateException("Expiró token");
        }

        tokenConfirmationService.setConfirmedAt(token);
        userService.enableAppUser(tokenConfirmation.getUser().getEmail());
        return "Confirmado";
    }
    
}
