package disney.api.services;

import disney.api.models.content.Genre;
import disney.api.payload.request.GenreRequest;
import disney.api.repository.GenreRepository;
import disney.api.services.Utils.ImgUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Service
public class GenreService {
  @Autowired private ImgUtils imgUtils;

  @Autowired private GenreRepository genreRepository;

  public Genre updateGenre(GenreRequest genreRequest) {
    Genre genre;
    Optional<Genre> optionalGenre =
        genreRepository.findOneByIdOrName(genreRequest.getId(), genreRequest.getName());
    genre = optionalGenre.orElseGet(Genre::new);
    return saveGenre(genre, genreRequest);
  }

  public Genre saveNewGenre(GenreRequest genreRequest) {
    genreRepository
        .findOneByName(genreRequest.getName())
        .orElseThrow(
            () ->
                new RuntimeException(
                    "Could not store Genre " + genreRequest.getName() + "is ready exist"));
    Genre genre = new Genre();
    return saveGenre(genre, genreRequest);
  }

  private Genre saveGenre(Genre genre, GenreRequest genreRequest) {
    try {
      genre.setName(genre.getName());

      return storeImgInGenre(genreRepository.save(genre), genreRequest.getFile());
    } catch (Exception ex) {
      throw new RuntimeException(
          "Could not store Genre " + genreRequest.getName() + ". Please try again!", ex);
    }
  }

  private Genre storeImgInGenre(Genre genre, MultipartFile file) throws IOException {
    genre.setImg(imgUtils.getImgData(file));
    return genreRepository.save(genre);
  }

  public Genre getGenre(Long genre_id) {
    return genreRepository
        .findById(genre_id)
        .orElseThrow(() -> new RuntimeException("File not found with id " + genre_id));
  }

  public void delete(Long genre_id) {
    genreRepository.delete(
        genreRepository
            .findById(genre_id)
            .orElseThrow(() -> new RuntimeException("Error: Movie is not found.")));
  }
}
