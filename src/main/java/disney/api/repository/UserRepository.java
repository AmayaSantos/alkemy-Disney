package disney.api.repository;

import disney.api.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
  Optional<User> findByUsername(String username);

  Optional<User> findOneByUsernameAndPassword(String username,String pass);


  Boolean existsByUsername(String username);


  Boolean existsByEmail(String email);
}
