package disney.api.repository;

import disney.api.models.content.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MovieRepository extends JpaRepository<Movie, Long> {

  Optional<Movie> findOneByTitle(String title);

  Optional<Movie> findOneByIdOrTitle(Long id, String title);
}
