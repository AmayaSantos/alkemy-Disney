package disney.api.controllers;

import disney.api.models.content.Character;
import disney.api.payload.request.CharacterRequest;
import disney.api.payload.response.CharacterResponse;
import disney.api.payload.response.MessageResponse;
import disney.api.services.CharacterService;
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
@RequestMapping("/characters")
public class CharacterControler {

  @Autowired CharacterService characterService;

  @PostMapping("")
  @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
  public ResponseEntity<?> addCharacter(@ModelAttribute CharacterRequest characterRequest) {

    try {
      Character character = characterService.saveCharacter(characterRequest);
      characterService.storeImgInCharacter(character, characterRequest.getFile());
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
    }
    return ResponseEntity.ok(new MessageResponse("Successful save Character!!"));
  }

  @GetMapping("/{caharacter_id}")
  @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
  public ResponseEntity<CharacterResponse> downloadFile(@PathVariable Long caharacter_id)  {
    // Load file from database
    Character character = characterService.getCharacter(caharacter_id);

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
