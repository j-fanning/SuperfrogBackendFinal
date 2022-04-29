package edu.tcu.cs.superfrogbackendfinal.Authentication.Services;

import edu.tcu.cs.superfrogbackendfinal.Authentication.Models.User;
import edu.tcu.cs.superfrogbackendfinal.Authentication.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {
    /**
     * Repository for basic operations: findAll(), findById(), delete(), save(), update()
     */
    private final UserRepository userRepository;
    @Autowired
    PasswordEncoder encoder;

    @Autowired
    public StudentService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }


    public User findById(final Long id) {
        return userRepository.findById(id).get();
    }

    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    public User save(User user) {
        return userRepository.save(user);
    }

    public User update(Long id, User user) {
        return userRepository.save(user);
    }

    public boolean findByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    private static final long EXPIRE_TOKEN_AFTER_MINUTES = 30;
}
