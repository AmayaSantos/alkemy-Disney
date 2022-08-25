package disney.api.controllers;

import disney.api.models.content.Movie;
import disney.api.payload.request.MovieRequest;
import disney.api.payload.response.MessageResponse;
import disney.api.services.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

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
      Movie movie = movieService.saveNewMovie(movieRequest);
      movieService.storeImgInMovie(movie, movieRequest.getFile());
      return ResponseEntity.ok().body(movie);
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
    }
  }
  @PostMapping("/{movie_id}")
  @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
  public ResponseEntity<?> updateMovie(MovieRequest movieRequest, @PathVariable Long movie_id)  {
    try{
      movieRequest.setId(movie_id);
      return ResponseEntity.ok().body(movieService.updateMovie(movieRequest));
    } catch (Exception e){
      return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
    }
  }

  @DeleteMapping("/{movie_id}")
  @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
  public ResponseEntity<?> deleteMovie(@PathVariable Long movie_id) {
    try {
      movieService.deleteMovie(movie_id);
      return ResponseEntity.ok().body("susserful Delete");
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
    }
  }

  @GetMapping("/{movie_id}")
  @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
  public ResponseEntity<?> getMovie(@PathVariable Long movie_id) {
    return ResponseEntity.ok().body(movieService.getMovie(movie_id));
  }

  @PostMapping("/{movie_id}/character/{character_id}")
  @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
  public ResponseEntity<?> addCharacterToMovie(@PathVariable Long movie_id,@PathVariable Long character_id){
    try{
      Movie movie = movieService.addCharacterToMovie(movie_id, character_id);
      return ResponseEntity.ok().body(movie);
    }catch (Exception e){
      return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
    }
  }

  @DeleteMapping("/{movie_id}/character/{character_id}")
  @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
  public ResponseEntity<?> deleteCharacterToMovie(@PathVariable Long movie_id,@PathVariable Long character_id){
    try{
      Movie movie = movieService.deleteCharacterToMovie(movie_id, character_id);
      return ResponseEntity.ok().body(movie);
    }catch (Exception e){
      return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
    }
  }
}
