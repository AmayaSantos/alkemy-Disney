package disney.api.payload.request;

import lombok.Data;
import org.hibernate.validator.constraints.Range;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Date;

@Data
public class MovieRequest {

  private Long id;

  @NotBlank
  @Size(max = 20)
  private String title;

  private Date creation_date;

  @Range(min = 1, max = 5)
  private Float qualification;

  private MultipartFile file;
}
