package disney.api.payload.request;

import lombok.Data;
import org.hibernate.validator.constraints.Range;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class CharacterRequest {

    @NotBlank
    @Size(max = 20)
    private String name;

    @Range(min = 0,max = 120)
    private Integer age;

    private Integer weight;
    @NotBlank
    @Size(max = 500)
    private String history;

    private MultipartFile file;

}
