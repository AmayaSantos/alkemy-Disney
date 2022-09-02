package disney.api.payload.response;

import disney.api.models.Role;
import disney.api.models.User;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;

@Data
@AllArgsConstructor
public class UserResponse {

  private String name;

  private Set<Role> roles;

  public UserResponse(User user) {
    this.name = user.getUsername();
    this.roles = user.getRoles();
  }
}
