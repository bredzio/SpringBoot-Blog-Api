package com.alkemy.blogAPI.security.token;

import java.time.LocalDateTime;
import java.util.Optional;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface TokenConfirmationRepository extends JpaRepository<TokenConfirmation, Long>{
    Optional<TokenConfirmation> findByToken(String token);
    
    @Transactional
    @Modifying
    @Query("UPDATE TokenConfirmation c " + "SET c.confirmedAt = ?2 " + "WHERE c.token = ?1")
    int updateConfirmedAt(String token, LocalDateTime confirmedAt);
}
