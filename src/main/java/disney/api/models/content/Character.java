package disney.api.models.content;

import disney.api.models.ImgData;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "personajes")
public class Character {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 20)
    private String name;

    @Range(min = 0,max = 120)
    private Integer age;

    private Integer weight;

    @NotBlank
    @Size(max = 500)
    private String history;

    @ManyToMany(mappedBy = "associatedCharacters", fetch = FetchType.EAGER)
    private List<Movie> films;

    private String fileName;

    private String fileType;

    @Lob
    private byte[] data;

    public Character(String name, Integer age, Integer weight, String history) {
        this.name = name;
        this.age = age;
        this.weight = weight;
        this.history = history;
    }




    public void setImg(ImgData imgData) {
        this.fileName= imgData.getFileName();
        this.fileType= imgData.getFileType();
        this.data= imgData.getData();
    }
}