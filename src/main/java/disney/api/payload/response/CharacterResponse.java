package disney.api.payload.response;

import disney.api.models.content.Character;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CharacterResponse {

  private String name;

  private byte[] img;

  public CharacterResponse(Character character) {
    this.name = character.getName();
    this.img = character.getData();
  }
}
