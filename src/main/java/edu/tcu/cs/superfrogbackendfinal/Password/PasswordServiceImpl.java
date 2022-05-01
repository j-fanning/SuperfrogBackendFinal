package edu.tcu.cs.superfrogbackendfinal.Password;

import edu.tcu.cs.superfrogbackendfinal.Authentication.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PasswordServiceImpl {

    @Autowired
    private UserRepository userRepository;

//    @Override
    public Optional findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

//    @Override
    public Optional findByResetToken(String resetToken) {
        return userRepository.findByResetToken(resetToken);
    }

//    @Override
//    public void save(User user) {
//        userRepository.save(user);
//    }
}
