package pl.rstepniewski.purchaselistapi.model.token;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, Integer> {

    @Query(""" 
            SELECT token from Token token
            WHERE  token.user.id = :userId
            AND   (token.expired = false OR token.revoked = false)
            """ )
    List<Token> findAllValidTokensByUser(Integer userId);

    Optional<Token> findByToken(String token);
}
