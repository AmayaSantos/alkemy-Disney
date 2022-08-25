package disney.api.controllers;

import disney.api.models.content.Character;
import disney.api.models.content.Movie;
import disney.api.payload.request.MovieRequest;
import disney.api.payload.response.CharacterResponse;
import disney.api.payload.response.MessageResponse;
import disney.api.services.MovieService;
import disney.api.services.Utils.ImageValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/movies")
public class MovieControler {

  @Autowired
  MovieService movieService;

  @PostMapping("")
  @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
  public ResponseEntity<?> addMovie(@ModelAttribute MovieRequest movieRequest) {

    try {
      Movie movie = movieService.saveMovie(movieRequest);
      movieService.storeImgInMovie(movie, movieRequest.getFile());
      return ResponseEntity.ok().body(movie);
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
    }


  }

  @GetMapping("/{movie_id}")
  @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
  public ResponseEntity<?> getMovie(@PathVariable Long movie_id) {
    return ResponseEntity.ok()
        .body(movieService.getMovie(movie_id));

  }

}
