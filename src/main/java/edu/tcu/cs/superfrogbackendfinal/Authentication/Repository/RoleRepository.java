package edu.tcu.cs.superfrogbackendfinal.Authentication.Repository;

import edu.tcu.cs.superfrogbackendfinal.Authentication.Models.ERole;
import edu.tcu.cs.superfrogbackendfinal.Authentication.Models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(ERole name);
}