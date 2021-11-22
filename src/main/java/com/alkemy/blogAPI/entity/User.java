package com.alkemy.blogAPI.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Data
@EqualsAndHashCode
@NoArgsConstructor
@Entity
@SQLDelete(sql = "UPDATE user SET enabled = false WHERE userId = ?")
public class User implements UserDetails{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer userId;
    
    private String email;
    private String password;
    private Boolean enabled;
    @Enumerated(EnumType.STRING)
    private RoleUser roleUser;
    
    @JsonManagedReference
    @OneToMany(mappedBy="user")
    private List<Post> posts;
    
    
    public User(String email, String password, RoleUser roleUser) {
        this.email = email;
        this.password = password;
        this.roleUser = roleUser;
        this.enabled = true;
    }

    
    
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(roleUser.name());
        return Collections.singletonList(authority);    }

    @Override
    public String getUsername() {
        return email;    
    }
    
    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;    
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;    
    }

    @Override
    public boolean isEnabled() {
        return enabled;    
    }


    

    
    
}
