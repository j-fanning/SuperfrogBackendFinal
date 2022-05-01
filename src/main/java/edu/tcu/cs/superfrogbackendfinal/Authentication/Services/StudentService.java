package edu.tcu.cs.superfrogbackendfinal.Authentication.Services;

import edu.tcu.cs.superfrogbackendfinal.Authentication.Models.ERole;
import edu.tcu.cs.superfrogbackendfinal.Authentication.Models.Role;
import edu.tcu.cs.superfrogbackendfinal.Authentication.Models.User;
import edu.tcu.cs.superfrogbackendfinal.Authentication.Repository.RoleRepository;
import edu.tcu.cs.superfrogbackendfinal.Authentication.Repository.UserRepository;
import edu.tcu.cs.superfrogbackendfinal.domain.Result;
import edu.tcu.cs.superfrogbackendfinal.domain.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class StudentService {
    /**
     * Repository for basic operations: findAll(), findById(), delete(), save(), update()
     */
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    @Autowired
    PasswordEncoder encoder;

    @Autowired
    public StudentService(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    public List<User> findAll() {

        List<User> allUsers = userRepository.findAll();
        List<User> allStudents = new ArrayList<>();
        for(int i = 0; i < allUsers.size(); i++)
            if (allUsers.get(i).getRoles().contains(ERole.ROLE_STUDENT)){
                allStudents.add(allUsers.get(i));
            }
        return allStudents;
    }


    public Result findById(final Long id, Long userID) {
        User fetchedUser = userRepository.findById(id).get();
        User user = userRepository.findById(userID).get();
        Set<Role> roles = user.getRoles();
        //if user is director, allow
        for (Role role : roles){
            System.out.println("TEST");
            if (role.getName().equals(ERole.ROLE_DIRECTOR)){
                return new Result(true, StatusCode.SUCCESS, "Find student success!", fetchedUser);
            }
        }
        //if not only allow user with matching ID
        if (fetchedUser.getId() == userID){
            return new Result(true, StatusCode.SUCCESS, "Find student success!", fetchedUser);
        }
        else{
            return new Result(false, StatusCode.UNAUTHORIZED, "You can only view your own account");
        }

    }
    public void delete(Long id) {
        User user = userRepository.findById(id).get();
        user.setEnabled(false);
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
        Role userRole = roleRepository.findByName(ERole.ROLE_CUSTOMER).orElseThrow(() -> new RuntimeException("Error: Role is not found."));
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
                return new Result(true, StatusCode.SUCCESS, "Updated student account!");
            }
        }
        if(id == user.getId()) {
            newPassword = encoder.encode(newPassword);
            user.setPassword(newPassword);
            newRoles.add(userRole);
            user.setRoles(newRoles);
            userRepository.save(user);
            //System.out.println("TEST3");
            return new Result(true, StatusCode.SUCCESS, "Updated student account!");
        }
        return new Result(false, StatusCode.UNAUTHORIZED, "Can only update your own account!");
    }

    public boolean findByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    private static final long EXPIRE_TOKEN_AFTER_MINUTES = 30;


}
