package edu.tcu.cs.superfrogbackendfinal.Authentication.Repository;

import edu.tcu.cs.superfrogbackendfinal.Authentication.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    Optional<User> findById(Long id);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);
}