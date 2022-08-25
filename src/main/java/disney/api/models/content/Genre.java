package disney.api.models.content;

import disney.api.models.ImgData;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "ganeros")
public class Genre {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "genero_pelicula",
            joinColumns = @JoinColumn(name = "id_genero"),
            inverseJoinColumns = @JoinColumn(name = "id_pelicula"))
    private List<Movie> films;


    private String fileName;

    private String fileType;

    @Lob
    private byte[] data;

    public void setImg(ImgData imgData) {
        this.fileName= imgData.getFileName();

        this.fileType= imgData.getFileType();

        this.data= imgData.getData();
    }
}
