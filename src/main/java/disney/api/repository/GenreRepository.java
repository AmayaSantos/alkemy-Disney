package disney.api.repository;

import disney.api.models.content.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GenreRepository extends JpaRepository<Genre, Long> {

  Optional<Genre> findOneByIdOrName(Long id, String name);

  Optional<Genre> findOneByName(String name);
}
