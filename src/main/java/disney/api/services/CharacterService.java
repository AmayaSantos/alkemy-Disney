package disney.api.services;

import disney.api.models.content.Character;
import disney.api.payload.request.CharacterRequest;
import disney.api.repository.CharacterRepository;
import disney.api.services.Utils.ImgUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Service
public class CharacterService {
    @Autowired
    private ImgUtils imgUtils;

    @Autowired
    private CharacterRepository characterRepository;


    public Character updateCharacter(CharacterRequest characterRequest) {
        Character character ;
        Optional<Character> optionalCharacter =
        characterRepository.findOneByIdOrName(characterRequest.getId(), characterRequest.getName());
        character = optionalCharacter.orElseGet(Character::new);
        return saveCharacter(character,characterRequest);
    }

    public Character saveNewCharacter(CharacterRequest characterRequest) {
        characterRepository.findOneByName(characterRequest.getName()).orElseThrow(
                ()-> new RuntimeException("Could not store Character " + characterRequest.getName() +"is ready exist"));
        Character character = new Character();
        return saveCharacter(character,characterRequest);
    }

    private Character saveCharacter(Character character,CharacterRequest characterRequest){
        try {
          character.setName(character.getName());
          character.setAge(characterRequest.getAge());
          character.setWeight(characterRequest.getWeight());
          character.setHistory(character.getHistory());

          return storeImgInCharacter(characterRepository.save(character), characterRequest.getFile());
        } catch (Exception ex) {
            throw new RuntimeException("Could not store Character " + characterRequest.getName() + ". Please try again!", ex);
        }
    }

    private Character storeImgInCharacter(Character character, MultipartFile file) throws IOException {
            character.setImg(imgUtils.getImgData(file));
            return characterRepository.save(character);
    }

    public Character getCharacter(Long character_id) {
        return characterRepository.findById(character_id)
                .orElseThrow(() -> new RuntimeException("File not found with id " + character_id));
    }

    public void delete(Long character_id) {
        characterRepository.delete(characterRepository.findById(character_id).orElseThrow(() -> new RuntimeException("Error: Movie is not found.")));
    }
}
