package edu.tcu.cs.superfrogbackendfinal.Password;

import edu.tcu.cs.superfrogbackendfinal.Authentication.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PasswordService extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    Optional<User> findByResetToken(String resetToken);
//    void save(User user);
}
