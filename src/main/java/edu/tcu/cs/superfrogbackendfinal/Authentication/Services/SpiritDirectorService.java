package edu.tcu.cs.superfrogbackendfinal.Authentication.Services;

import edu.tcu.cs.superfrogbackendfinal.Authentication.Models.ERole;
import edu.tcu.cs.superfrogbackendfinal.Authentication.Models.Role;
import edu.tcu.cs.superfrogbackendfinal.Authentication.Models.User;
import edu.tcu.cs.superfrogbackendfinal.Authentication.Payload.Request.SignupRequest;
import edu.tcu.cs.superfrogbackendfinal.Authentication.Payload.Response.MessageResponse;
import edu.tcu.cs.superfrogbackendfinal.Authentication.Repository.RoleRepository;
import edu.tcu.cs.superfrogbackendfinal.Authentication.Repository.UserRepository;
import edu.tcu.cs.superfrogbackendfinal.domain.Result;
import edu.tcu.cs.superfrogbackendfinal.domain.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.util.ArrayList;
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
        List<User> allUsers = userRepository.findAll();
        List<User> allDirectors = new ArrayList<>();
        Set<Role> newRoles;
        for (int i = 0; i < allUsers.size(); i++) {
            newRoles = allUsers.get(i).getRoles();
            for (Role role : newRoles) {
                //System.out.println("TEST");
                if (role.getName().equals(ERole.ROLE_DIRECTOR)) {
                    allDirectors.add(allUsers.get(i));
                }
            }
        }
        return allDirectors;
    }


    public User findById(final Long id) {
        return userRepository.findById(id).get();
    }

    public void delete(Long id) {
        User user = userRepository.findById(id).get();
        if(user.isEnabled()) user.setEnabled(false);
        else user.setEnabled(true);
        userRepository.save(user);
    }

    public User save(User user) {
        return userRepository.save(user);
    }

    public Result update(Long id, User user) {
        //tempuser is user that is doing the updating
        User tempUser = userRepository.getById(id);
        //new password (if its changed)
        String newPassword = user.getPassword();
        Role userRole = roleRepository.findByName(ERole.ROLE_DIRECTOR).orElseThrow(() -> new RuntimeException("Error: Role is not found."));
        Set<Role> newRoles = new HashSet<>();
        Set<Role> roles = tempUser.getRoles();
        for (Role role : roles) {
            //System.out.println("TEST");
            if (role.getName().equals(ERole.ROLE_DIRECTOR)) {
                newPassword = encoder.encode(newPassword);
                user.setPassword(newPassword);
                newRoles.add(userRole);
                user.setRoles(newRoles);
                userRepository.save(user);
                return new Result(true, StatusCode.SUCCESS, "Updated Director account!");
            }
        }
        return new Result(true, StatusCode.SUCCESS, "Updated Director account!");
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

    public List<User> getRoster() {
        List<User> allUsers = userRepository.findAll();
        List<User> allActive = new ArrayList<>();
        Set<Role> newRoles;
        for (int i = 0; i < allUsers.size(); i++) {
            newRoles = allUsers.get(i).getRoles();
            for (Role role : newRoles) {
                //System.out.println("TEST");
                if (role.getName().equals(ERole.ROLE_STUDENT) & allUsers.get(i).isEnabled()) {
                    allActive.add(allUsers.get(i));
                }
            }
        }
        return allActive;
    }
}
