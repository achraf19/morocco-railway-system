package railway.moroccorailwaysystem.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import railway.moroccorailwaysystem.model.User;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    boolean existsByEmail(String email);
    Optional<User> findUserByEmail(String email);
}
