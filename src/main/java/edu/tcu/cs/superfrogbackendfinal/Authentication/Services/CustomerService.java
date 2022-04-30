package edu.tcu.cs.superfrogbackendfinal.Authentication.Services;

import edu.tcu.cs.superfrogbackendfinal.Authentication.Models.ERole;
import edu.tcu.cs.superfrogbackendfinal.Authentication.Models.Role;
import edu.tcu.cs.superfrogbackendfinal.Authentication.Models.User;
import edu.tcu.cs.superfrogbackendfinal.Authentication.Repository.UserRepository;
import edu.tcu.cs.superfrogbackendfinal.domain.Result;
import edu.tcu.cs.superfrogbackendfinal.domain.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

@Service
public class CustomerService {
    /**
     * Repository for basic operations: findAll(), findById(), delete(), save(), update()
     */
    private final UserRepository userRepository;
    @Autowired
    PasswordEncoder encoder;

    @Autowired
    public CustomerService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }


    public Result findById(final Long id, Long userID) {
        User fetchedUser = userRepository.findById(id).get();
        User user = userRepository.findById(userID).get();
        Set<Role> roles = user.getRoles();
        //if user is director, allow
        for (Role role : roles){
            System.out.println("TEST");
            if (role.getName().equals(ERole.ROLE_DIRECTOR)){
                return new Result(true, StatusCode.SUCCESS, "", fetchedUser);
            }
        }
        //if not only allow user with matching ID
        if (fetchedUser.getId() == userID){
            return new Result(true, StatusCode.SUCCESS, "", fetchedUser);
        }
        else{
            return new Result(false, StatusCode.UNAUTHORIZED, "You can only view your own account");
        }

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
