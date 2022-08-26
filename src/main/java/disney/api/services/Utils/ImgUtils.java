package disney.api.services.Utils;

import disney.api.models.ImgData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Component
public class ImgUtils {
  @Autowired private ImageValidator imageValidator;

  public ImgData getImgData(MultipartFile file) throws RuntimeException, IOException {
    Errors errors = new BeanPropertyBindingResult(file, "imgagen");
    imageValidator.validate(file, errors);

    if (errors.hasErrors()) {
      throw new RuntimeException(errors.getAllErrors().toString());
    }
    return new ImgData(file.getOriginalFilename(), file.getContentType(), file.getBytes());
  }
}
