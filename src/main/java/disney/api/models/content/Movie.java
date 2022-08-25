package disney.api.models.content;

import disney.api.models.ImgData;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "pelicula")
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 20)
    private String title;

    private Date creation_date;

    @Range(min = 1,max = 5)
    private Float qualification;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "pelicula_personaje",
            joinColumns = @JoinColumn(name = "id_pelicula"),
            inverseJoinColumns = @JoinColumn(name = "id_personaje"))
    private List<Character> associatedCharacters;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "genero_pelicula",
            joinColumns = @JoinColumn(name = "id_genero"),
            inverseJoinColumns = @JoinColumn(name = "id_pelicula"))
    private List<Genre> genres;

    private String fileName;

    private String fileType;

    @Lob
    private byte[] data;

    public void setImg(ImgData imgData) {
        this.fileName= imgData.getFileName();
        this.fileType= imgData.getFileType();
        this.data= imgData.getData();
    }

    public void addCharacter(Character character) {
        if (associatedCharacters.isEmpty()){
            this.associatedCharacters = new ArrayList<>();
        }
        this.associatedCharacters.add(character);
    }

    public void deleteCharacter(Character character) {
        if (associatedCharacters.contains(character)) {
            this.associatedCharacters.remove(character);
        }
    }
}
