package disney.api.payload.request;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class GenreRequest {

  private Long id;

  @NotBlank
  @Size(max = 20)
  private String name;

  private MultipartFile file;
}
