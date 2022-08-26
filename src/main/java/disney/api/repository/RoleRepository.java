package disney.api.repository;

import disney.api.models.ERole;
import disney.api.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
  Role findByName(ERole name);
}
