package disney.api.services;

import disney.api.models.ERole;
import disney.api.models.Role;
import disney.api.models.User;
import disney.api.repository.RoleRepository;
import disney.api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

  @Autowired UserRepository userRepository;

  @Autowired RoleRepository roleRepository;

  public void register(String username, String email, String encode) {
    User user = new User(username, email, encode);
    user.addRole(roleRepository.findByName(ERole.ROLE_USER));
    userRepository.save(user);
  }

  public boolean existsUser(String username, String email) {
    return userRepository.existsByUsername(username) || userRepository.existsByUsername(email);
  }

  public String whoExistsUser(String username, String email) {
    if (userRepository.existsByUsername(username)) {
      return username;
    } else {
      return email;
    }
  }

  public void setRole(Long idUser, Integer idRole) {
    User user =
        userRepository
            .findById(idUser)
            .orElseThrow(() -> new RuntimeException("Error: User is not found."));
    Role role =
        roleRepository
            .findById(idRole)
            .orElseThrow(() -> new RuntimeException("Error: User is not found."));

    user.addRole(role);
    userRepository.save(user);
  }
}
