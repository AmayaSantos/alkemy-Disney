package disney.api.services;

import disney.api.models.content.Character;
import disney.api.models.content.Movie;
import disney.api.payload.request.CharacterRequest;
import disney.api.payload.request.MovieRequest;
import disney.api.repository.CharacterRepository;
import disney.api.repository.MovieRepository;
import disney.api.services.Utils.ImgUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;

@Service
public class MovieService {

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private ImgUtils imgUtils;

    public Movie saveMovie(MovieRequest movieRequest) {

        try {
      Movie movie =
          new Movie(null, movieRequest.getTitle(), movieRequest.getCreation_date(),
                  movieRequest.getQualification(),new ArrayList<>(),new ArrayList<>(),null,null,null);
          return movieRepository.save(movie);
        } catch (RuntimeException ex) {
            throw new RuntimeException("Could not store Character " + movieRequest.getTitle() + ". Please try again!", ex);
        }
    }

    public void storeImgInMovie(Movie movie, MultipartFile file) throws IOException {
            movie.setImg(imgUtils.getImgData(file));
            movieRepository.save(movie);
    }

    public Movie getMovie(Long movie_id) {
        return movieRepository.findById(movie_id)
                .orElseThrow(() -> new RuntimeException("File not found with id " + movie_id));
    }
}
