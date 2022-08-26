package disney.api.controllers;

import disney.api.models.content.Genre;
import disney.api.payload.request.GenreRequest;
import disney.api.payload.response.MessageResponse;
import disney.api.services.GenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/genre")
public class GenreControler {

  @Autowired GenreService genreService;

  @PostMapping("")
  @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
  public ResponseEntity<?> addGenre(@ModelAttribute GenreRequest genreRequest) {
    try {
      return ResponseEntity.ok(genreService.saveNewGenre(genreRequest));
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
    }
  }

  @PostMapping("/{genre_id}")
  @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
  public ResponseEntity<?> updateGenre(GenreRequest genreRequest, @PathVariable Long genre_id) {
    try {
      genreRequest.setId(genre_id);
      return ResponseEntity.ok().body(genreService.updateGenre(genreRequest));
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
    }
  }

  @DeleteMapping("/{genre_id}")
  @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
  public ResponseEntity<?> deleteGenre(@PathVariable Long genre_id) {
    try {
      genreService.delete(genre_id);
      return ResponseEntity.ok().body("Delete Successful");
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
    }
  }

  @GetMapping("/{genre_id}")
  @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
  public ResponseEntity<?> getGenre(@PathVariable Long genre_id) {
    Genre genre = genreService.getGenre(genre_id);
    return ResponseEntity.ok().body(genre);
  }
}
