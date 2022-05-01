package edu.tcu.cs.superfrogbackendfinal.Password;

import edu.tcu.cs.superfrogbackendfinal.Authentication.Models.User;
import edu.tcu.cs.superfrogbackendfinal.Authentication.Repository.UserRepository;
import edu.tcu.cs.superfrogbackendfinal.domain.Result;
import edu.tcu.cs.superfrogbackendfinal.domain.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/customers")
public class PasswordController {

    @Autowired
    private PasswordServiceImpl passwordService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private PasswordEncoder bCryptPasswordEncoder;

//    @RequestMapping(value ="/forgot", method = RequestMethod.GET)
//    @GetMapping("/forgot")
//    public ModelAndView displayForgotPasswordPage() {
//        return new ModelAndView("forgotPassword");
//    }


    @GetMapping("/forgot")
    public Result processForgotPasswordForm(HttpServletRequest request, @RequestBody String userEmail) {

        Optional<User> optional = userRepository.findByEmail(userEmail);
//        System.out.println(userEmail);
        Result res;


        if(optional.isPresent()) {

            User user = optional.get();
            user.setResetToken(UUID.randomUUID().toString());

            userRepository.save(user);

            String aURL = request.getScheme() + "://" + request.getServerName();

            SimpleMailMessage resetEmail = new SimpleMailMessage();
            resetEmail.setFrom("no-reply@superfrog.com");
            resetEmail.setTo(user.getEmail());
            resetEmail.setSubject("Password Reset Request");
            resetEmail.setText("To reset your password, click on the link below:\n" + aURL +
                    "/reset?token=" +user.getResetToken());

            emailService.sendEmail(resetEmail);

            res = new Result(true, StatusCode.SUCCESS, "A password reset link has been sent" +
                    "to your email");



        } else {
            res = new Result(false, StatusCode.FAILURE, "We didn't find an account for that email " +
                    "address");
        }
//        modelAndView.setViewName("forgotPassword");

        return res;
    }

    @PostMapping("/reset")
    public Result setNewPassword(@RequestParam Map<String, String> requestParams,
                                       RedirectAttributes redirect) {
        Optional<User> user = passwordService.findByResetToken(requestParams.get("token"));
        Result res;

        if (user.isPresent()) {
            User resetUser = user.get();

            resetUser.setPassword(bCryptPasswordEncoder.encode(requestParams.get("password")));

            resetUser.setResetToken(null);

            userRepository.save(resetUser);

            res = new Result(true, StatusCode.SUCCESS, "You have successfully reset your password." +
                    "You may now login.");
            return res;
        } else {
            res = new Result(true, StatusCode.FAILURE, "This is an invalid reset link");
//            modelAndView.setViewName("resetPassword");
        }
        return res;
    }

    @ExceptionHandler(MissingServletRequestPartException.class)
    public ModelAndView handleMissingParams(MissingServletRequestPartException ex) {
        return new ModelAndView("redirect:login");
    }

}
