package disney.api.repository;


import disney.api.models.content.Character;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.Optional;

@Repository
public interface CharacterRepository extends JpaRepository<Character, Long> {

    Optional<Character> findOneByIdOrName(Long id, String name);

    Optional<Character> findOneByName(String name);

}
