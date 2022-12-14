package disney.api.models;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Role {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Enumerated(EnumType.STRING)
  @Column(length = 20)
  private ERole name;

  public Role() {}

  public Role(ERole name) {
    this.name = name;
  }
}
