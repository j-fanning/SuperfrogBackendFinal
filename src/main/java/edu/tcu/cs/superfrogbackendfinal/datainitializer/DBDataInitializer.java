package edu.tcu.cs.superfrogbackendfinal.datainitializer;

import edu.tcu.cs.superfrogbackendfinal.Authentication.Repository.RoleRepository;
import edu.tcu.cs.superfrogbackendfinal.Authentication.Repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DBDataInitializer implements CommandLineRunner {

    private UserRepository userRepository;
    private RoleRepository roleRepository;

    public DBDataInitializer(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public void run(String... args) throws Exception {
/*
        Set<Role> roles = new HashSet<>();
        Role role = new Role();
        role.setName(ERole.ROLE_DIRECTOR);
        roles.add(role);
        User s1 = new User();
        s1.setUsername("James");
        s1.setPassword("123456");
        s1.setEmail("j.fanning@tcu.edu");
        s1.setEnabled(false);
        s1.setRoles(roles);
        userRepository.save(s1);
*/
    }
}
