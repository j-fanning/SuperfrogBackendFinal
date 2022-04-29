package edu.tcu.cs.superfrogbackendfinal.Authentication.Controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/test")
public class TestController {
    @GetMapping("/all")
    public String allAccess() {
        return "Public Content.";
    }

    @GetMapping("/test-customer")
    @PreAuthorize("hasRole('CUSTOMER') or hasRole('STUDENT') or hasRole('DIRECTOR')")
    public String userAccess() {
        return "Customer Content.";
    }

    @GetMapping("/test-student")
    @PreAuthorize("hasRole('STUDENT')")
    public String moderatorAccess() {
        return "Student Board.";
    }

    @GetMapping("/test-director")
    @PreAuthorize("hasRole('DIRECTOR')")
    public String adminAccess() {
        return "Director Board.";
    }
}