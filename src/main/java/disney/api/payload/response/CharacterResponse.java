package disney.api.payload.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CharacterResponse {
    private Long id;

    private String name;

    private Integer age;

    private Integer weight;

    private String history;

    private byte[] img;

}
