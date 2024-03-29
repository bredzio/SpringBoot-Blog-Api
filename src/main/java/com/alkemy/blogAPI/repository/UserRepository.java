package com.alkemy.blogAPI.repository;

import com.alkemy.blogAPI.entity.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


@Repository
@Transactional
public interface UserRepository extends JpaRepository<User,Integer>{
    User findByEmail(String email);
    
    Optional<User> existsUsuarioByEmail(String email);

    @Transactional
    @Modifying
    @Query("UPDATE User a " + "SET a.enabled = TRUE WHERE a.email = ?1")
    int enableAppUser(String email);
}
