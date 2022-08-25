package disney.api.controllers;

import disney.api.models.content.Character;
import disney.api.payload.request.CharacterRequest;
import disney.api.payload.response.CharacterResponse;
import disney.api.payload.response.MessageResponse;
import disney.api.services.CharacterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/characters")
public class CharacterControler {

  @Autowired CharacterService characterService;

  @PostMapping("")
  @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
  public ResponseEntity<?> addCharacter(@ModelAttribute CharacterRequest characterRequest) {
    try {
      return ResponseEntity.ok(characterService.saveNewCharacter(characterRequest));
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
    }
  }

  @PostMapping("/{character_id}")
  @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
  public ResponseEntity<?> updateCharacter(CharacterRequest characterRequest,@PathVariable Long character_id)  {
    try{
      characterRequest.setId(character_id);
      return ResponseEntity.ok().body(characterService.updateCharacter(characterRequest));
    } catch (Exception e){
      return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
    }
  }

  @DeleteMapping("/{character_id}")
  @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
  public ResponseEntity<?> deleteCharacter(@PathVariable Long character_id)  {
    try{
      characterService.delete(character_id);
      return ResponseEntity.ok().body("Delete Successful");
    } catch (Exception e){
      return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
    }
  }

  @GetMapping("/{character_id}")
  @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
  public ResponseEntity<CharacterResponse> getCharacter(@PathVariable Long character_id)  {
    Character character = characterService.getCharacter(character_id);
    return ResponseEntity.ok()
        .body(
            new CharacterResponse(
                    character.getId(),
                    character.getName(),
                    character.getAge(),
                    character.getWeight(),
                    character.getHistory(),
                    character.getData()));
  }

}
