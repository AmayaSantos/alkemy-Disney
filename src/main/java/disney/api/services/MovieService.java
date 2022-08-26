package disney.api.services;

import disney.api.models.content.Movie;
import disney.api.payload.request.MovieRequest;
import disney.api.repository.MovieRepository;
import disney.api.services.Utils.ImgUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Service
public class MovieService {

  @Autowired private MovieRepository movieRepository;
  @Autowired private CharacterService characterService;

  @Autowired private ImgUtils imgUtils;

  public Object updateMovie(MovieRequest movieRequest) {
    Movie movie;
    Optional<Movie> optionalMovie =
        movieRepository.findOneByIdOrTitle(movieRequest.getId(), movieRequest.getTitle());
    movie = optionalMovie.orElseGet(Movie::new);
    return saveMovie(movie, movieRequest);
  }

  public Movie saveNewMovie(MovieRequest movieRequest) {
    movieRepository
        .findOneByTitle(movieRequest.getTitle())
        .orElseThrow(
            () ->
                new RuntimeException(
                    "Could not store Movie " + movieRequest.getTitle() + "is ready exist"));
    Movie movie = new Movie();
    return saveMovie(movie, movieRequest);
  }

  private Movie saveMovie(Movie movie, MovieRequest movieRequest) {
    try {
      movie.setTitle(movieRequest.getTitle());
      movie.setCreation_date(movieRequest.getCreation_date());
      movie.setQualification(movie.getQualification());
      movie = movieRepository.save(movie);
      return storeImgInMovie(movie, movieRequest.getFile());
    } catch (Exception ex) {
      throw new RuntimeException(
          "Could not store Character " + movieRequest.getTitle() + ". Please try again!", ex);
    }
  }

  public Movie storeImgInMovie(Movie movie, MultipartFile file) throws IOException {
    movie.setImg(imgUtils.getImgData(file));
    return movieRepository.save(movie);
  }

  public Movie getMovie(Long movie_id) {
    return movieRepository
        .findById(movie_id)
        .orElseThrow(() -> new RuntimeException("File not found with id " + movie_id));
  }

  public Movie addCharacterToMovie(Long movie_id, Long character_id) {
    Movie movie =
        movieRepository
            .findById(movie_id)
            .orElseThrow(() -> new RuntimeException("Error: Movie is not found."));
    movie.addCharacter(characterService.getCharacter(character_id));
    return movieRepository.save(movie);
  }

  public Movie deleteCharacterToMovie(Long movie_id, Long character_id) {
    Movie movie =
        movieRepository
            .findById(movie_id)
            .orElseThrow(() -> new RuntimeException("Error: Movie is not found."));
    movie.deleteCharacter(characterService.getCharacter(character_id));
    return movieRepository.save(movie);
  }

  public void deleteMovie(Long movie_id) {
    movieRepository.delete(
        movieRepository
            .findById(movie_id)
            .orElseThrow(() -> new RuntimeException("Error: Movie is not found.")));
  }
}
