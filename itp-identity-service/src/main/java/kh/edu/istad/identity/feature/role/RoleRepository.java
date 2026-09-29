package kh.edu.istad.identity.feature.role;

import kh.edu.istad.identity.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByRole(String role);
}
