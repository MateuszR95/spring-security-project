package pl.mateusz.example.springsecurityproject.role;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRoleRepository extends JpaRepository<UserRole, Long> {

    Optional<UserRole> getUserRoleByRole(Role role);


}
