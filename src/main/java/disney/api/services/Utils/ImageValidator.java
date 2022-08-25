package disney.api.services.Utils;


import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.multipart.MultipartFile;

import java.util.Objects;

@Component
public class ImageValidator implements Validator {
    @Override
    public boolean supports(Class<?> aClass) {
        return MultipartFile.class.isAssignableFrom(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {

        MultipartFile imageFile = (MultipartFile) o;

        Objects.requireNonNull(imageFile);

        String fileName = StringUtils.cleanPath(Objects.requireNonNull(imageFile.getOriginalFilename()));

        if( fileName.contains("..")) {
            errors.rejectValue( "Sorry! Filename contains invalid path sequence " ,"file.invalid.name");
        }
        if(!(Objects.requireNonNull(imageFile.getContentType()).contains("image/jpeg") ||
                imageFile.getContentType().contains("image/png") ||
                imageFile.getContentType().contains("image/gif")
        ))
        {
            errors.rejectValue("file","file.invalid.type");
        }

    }

}
