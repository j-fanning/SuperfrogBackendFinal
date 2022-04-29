package edu.tcu.cs.superfrogbackendfinal.Authentication.Services;

import edu.tcu.cs.superfrogbackendfinal.Authentication.Models.ERole;
import edu.tcu.cs.superfrogbackendfinal.Authentication.Models.Role;
import edu.tcu.cs.superfrogbackendfinal.Authentication.Models.User;
import edu.tcu.cs.superfrogbackendfinal.Authentication.Payload.Request.SignupRequest;
import edu.tcu.cs.superfrogbackendfinal.Authentication.Payload.Response.MessageResponse;
import edu.tcu.cs.superfrogbackendfinal.Authentication.Repository.RoleRepository;
import edu.tcu.cs.superfrogbackendfinal.Authentication.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class SpiritDirectorService {
    /**
     * Repository for basic operations: findAll(), findById(), delete(), save(), update()
     */
    private final UserRepository userRepository;
    @Autowired
    PasswordEncoder encoder;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    public SpiritDirectorService(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
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

    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Username is already taken!"));
        }

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already in use!"));
        }
        // Create new user's account
        User user = new User(signUpRequest.getUsername(),
                signUpRequest.getEmail(),
                encoder.encode(signUpRequest.getPassword()));

        Set<String> strRoles = signUpRequest.getRole();
        Set<Role> roles = new HashSet<>();
        System.out.println("test3");
        if (strRoles == null) {
            Role userRole = roleRepository.findByName(ERole.ROLE_STUDENT)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    default:
                        Role userRole = roleRepository.findByName(ERole.ROLE_STUDENT)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(userRole);
                }
            });
        }

        user.setRoles(roles);
        userRepository.save(user);

        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }
}
