package disney.api.services;

import disney.api.models.content.Character;
import disney.api.payload.request.CharacterRequest;
import disney.api.repository.CharacterRepository;
import disney.api.services.Utils.ImgUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class CharacterService {
    @Autowired
    private ImgUtils imgUtils;

    @Autowired
    private CharacterRepository characterRepository;


    public Character saveCharacter(CharacterRequest characterRequest) {

        try {
          Character character = new Character(characterRequest.getName(),characterRequest.getAge(),
                  characterRequest.getWeight(),characterRequest.getHistory());
          return characterRepository.save(character);
        } catch (Exception ex) {
            throw new RuntimeException("Could not store Character " + characterRequest.getName() + ". Please try again!", ex);
        }
    }

    public void storeImgInCharacter(Character character, MultipartFile file) throws IOException {
            character.setImg(imgUtils.getImgData(file));
            characterRepository.save(character);

    }

    public Character getCharacter(Long character_id) {
        return characterRepository.findById(character_id)
                .orElseThrow(() -> new RuntimeException("File not found with id " + character_id));
    }
}
