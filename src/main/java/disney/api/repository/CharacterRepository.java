package disney.api.repository;

import disney.api.models.content.Character;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CharacterRepository extends JpaRepository<Character, Long> {

  Optional<Character> findOneByIdOrName(Long id, String name);

  Optional<Character> findOneByName(String name);

  List<Character> findByAge(Integer age);

  @Query("SELECT c FROM Character AS c JOIN c.films AS f WHERE (f.title = :title ) ")
  List<Character> findByFilms(@Param("title") String title);
}
