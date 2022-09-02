package disney.api.services;

import disney.api.models.ERole;
import disney.api.models.Role;
import disney.api.models.User;
import disney.api.repository.RoleRepository;
import disney.api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
  @Autowired PasswordEncoder encoder;

  @Autowired UserRepository userRepository;

  @Autowired RoleRepository roleRepository;

  public void register(String username, String email, String pass) {
    User user = new User(username, email, encoder.encode(pass));
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

  public User getUser(Long idUser) {
    return userRepository
        .findById(idUser)
        .orElseThrow(() -> new RuntimeException("Error: User is not found."));
  }

  public void changePass(String name, String pass, String repass, String oldpass) {
    if (pass.equals(repass)) throw new RuntimeException("Plis  inset same pass and repass");
    User user =
        userRepository
            .findOneByUsernameAndPassword(name, encoder.encode(oldpass))
            .orElseThrow(() -> new RuntimeException("Error: User Bad Credentials."));
    user.setPassword(encoder.encode(pass));
  }
}
