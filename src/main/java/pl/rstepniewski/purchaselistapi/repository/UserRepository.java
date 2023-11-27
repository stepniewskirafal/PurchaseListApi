package pl.rstepniewski.purchaselistapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.rstepniewski.purchaselistapi.domain.User;
import pl.rstepniewski.purchaselistapi.exception.EtAuthException;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findUserByEmail(String email) throws EtAuthException;
    boolean existsUserByEmail(String email) throws EtAuthException;
}
