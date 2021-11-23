package com.alkemy.blogAPI.service;

import com.alkemy.blogAPI.entity.User;
import com.alkemy.blogAPI.repository.UserRepository;
import com.alkemy.blogAPI.security.token.TokenConfirmation;
import com.alkemy.blogAPI.security.token.TokenConfirmationService;
import java.time.LocalDateTime;
import java.util.UUID;
import javax.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Service
@AllArgsConstructor
public class UserService implements UserDetailsService{
    private final static String userNotFound = "No se encontró usuario con ese email %s";
    
    @Autowired
    private final UserRepository userRepository;
    
    @Autowired
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final TokenConfirmationService tokenConfirmationService;

    
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);
        if(!userRepository.existsUsuarioByEmail(email).isPresent()){
            throw new UsernameNotFoundException(String.format(userNotFound, email));
        }
        
        GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_USER"+ user.getRoleUser());
        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        
        HttpSession sesion = attr.getRequest().getSession(true);
  
        sesion.setAttribute("userId", user.getUserId());
        sesion.setAttribute("email", user.getEmail());
        
        return new User(user.getEmail(),user.getPassword(),user.getRoleUser());
    }
    
    public User findByEmail(String email){
        return userRepository.findByEmail(email);
    }
    
    public String enableUser(User user) {
        boolean existUser = userRepository.existsUsuarioByEmail(user.getEmail()).isPresent();

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
